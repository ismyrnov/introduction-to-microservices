package com.epam.ismyrnov.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import com.epam.ismyrnov.client.SongClient;
import com.epam.ismyrnov.client.dto.SongId;
import com.epam.ismyrnov.dto.File;
import com.epam.ismyrnov.dto.FileId;
import com.epam.ismyrnov.dto.SongMetadata;
import com.epam.ismyrnov.enums.Mp3Metadata;
import com.epam.ismyrnov.exception.ServiceException;
import com.epam.ismyrnov.exception.ValidationException;
import com.epam.ismyrnov.persistence.ResourceRepository;
import com.epam.ismyrnov.persistence.entity.ResourceEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService {
  private final ResourceRepository resourceRepository;

  private final SongClient songClient;

  @Transactional
  public FileId upload(File request) {
    byte[] data = Base64.getDecoder().decode(request.getBase64Data());
    ResourceEntity resourceEntity = ResourceEntity.builder().data(data).build();
    ResourceEntity savedResourceEntity = resourceRepository.save(resourceEntity);

    SongMetadata songMetadata = getSongMetadata(savedResourceEntity.getId(), data);
    SongId songId = songClient.create(songMetadata);
    log.debug("Created song metadata with id {}", songId.getId());
    return FileId.builder().id(savedResourceEntity.getId()).build();
  }

  private SongMetadata getSongMetadata(Long resourceId, byte[] data) {
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
              .resourceId(resourceId.toString())
              .build();
      log.debug("Song metadata {}", songMetadata);
      return songMetadata;
    } catch (IOException | IllegalArgumentException e) {
      throw new ValidationException("Invalid file format", e);
    } catch (TikaException | SAXException e) {
      throw new ServiceException("An internal server error has occurred", e);
    }
  }
}
