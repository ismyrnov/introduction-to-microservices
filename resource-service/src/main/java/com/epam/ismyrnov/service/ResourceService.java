package com.epam.ismyrnov.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.xml.sax.SAXException;

import com.epam.ismyrnov.dto.Resource;
import com.epam.ismyrnov.dto.ResourceId;
import com.epam.ismyrnov.dto.ResourceIds;
import com.epam.ismyrnov.dto.SongMetadata;
import com.epam.ismyrnov.enums.Mp3Metadata;
import com.epam.ismyrnov.exception.ResourceNotFoundException;
import com.epam.ismyrnov.exception.ServiceException;
import com.epam.ismyrnov.exception.ValidationException;
import com.epam.ismyrnov.persistence.ResourceRepository;
import com.epam.ismyrnov.persistence.entity.ResourceEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService {
  private static final String S3_KEY_PATTERN = "%s.mp3";
  private final ResourceRepository resourceRepository;

  private final S3Client s3Client;

  @Value("${aws.s3.bucket}")
  private String bucketName;

  @Transactional
  public ResourceId upload(Resource request) {
    byte[] data = Base64.getDecoder().decode(request.getBase64Data());
    //    SongMetadata songMetadata = getSongMetadata(data);
    String fileLocation = String.format(S3_KEY_PATTERN, request.getFileName());

    ResourceEntity savedResourceEntity =
        resourceRepository.save(ResourceEntity.builder().location(fileLocation).build());

    uploadToStorage(data, fileLocation);
    //    SongId songId = songClient.create(songMetadata);
    //    log.debug("Created song metadata with id {}", songId.getId());
    return ResourceId.builder().id(savedResourceEntity.getId()).build();
  }

  @Transactional
  public ResourceIds delete(List<Long> ids) {
    List<ResourceEntity> songs = resourceRepository.findAllById(ids);
    List<Long> existingIds = songs.stream().map(ResourceEntity::getId).collect(Collectors.toList());
    resourceRepository.deleteAllById(existingIds);
    return ResourceIds.builder().ids(existingIds).build();
  }

  public Resource getResource(Integer id) {
    return resourceRepository
        .findById(Long.valueOf(id))
        .map(
            file ->
                Resource.builder()
                    //                    .base64Data(new
                    // String(Base64.getEncoder().encode(file.getData()))) TODO
                    .build())
        .orElseThrow(
            () ->
                new ResourceNotFoundException("The resource with the specified id does not exist"));
  }

  private SongMetadata getSongMetadata(
      //      Long resourceId,
      byte[] data) {
    try (final InputStream inputStream = new ByteArrayInputStream(data)) {
      BodyContentHandler handler = new BodyContentHandler();
      Metadata metadata = new Metadata();
      ParseContext pcontext = new ParseContext();
      Mp3Parser mp3Parser = new Mp3Parser();
      mp3Parser.parse(inputStream, handler, metadata, pcontext);

      SongMetadata songMetadata =
          SongMetadata.builder()
              .name(metadata.get(Mp3Metadata.NAME.getValue()))
              .album(metadata.get(Mp3Metadata.ALBUM.getValue()))
              .artist(metadata.get(Mp3Metadata.ARTIST.getValue()))
              .year(metadata.get(Mp3Metadata.YEAR.getValue()))
              .length(metadata.get(Mp3Metadata.LENGTH.getValue()))
              //              .resourceId(resourceId.toString())
              .build();
      log.debug("Song metadata {}", songMetadata);
      return songMetadata;
    } catch (IOException | IllegalArgumentException e) {
      throw new ValidationException("Invalid file format", e);
    } catch (TikaException | SAXException e) {
      throw new ServiceException("An internal server error has occurred", e);
    }
  }

  private void uploadToStorage(byte[] data, String key) {
    log.info("Started uploading to S3 file '{}'", key);
    try {
      PutObjectRequest request =
          PutObjectRequest.builder()
              .bucket(bucketName)
              .key(key)
              .contentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.toString())
              .build();
      s3Client.putObject(
          request,
          RequestBody.fromByteBuffer(
              ByteBuffer.wrap(new ByteArrayInputStream(data).readAllBytes())));
      log.info("Finished uploading to S3 file '{}'", key);
    } catch (SdkClientException exception) {
      throw new ServiceException("AWS S3 storage error: can't upload object", exception);
    }
  }
}
