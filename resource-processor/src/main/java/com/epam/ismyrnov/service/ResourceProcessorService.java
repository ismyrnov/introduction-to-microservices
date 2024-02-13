package com.epam.ismyrnov.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.epam.ismyrnov.client.ResourceClient;
import com.epam.ismyrnov.client.SongClient;
import com.epam.ismyrnov.client.dto.SongId;
import com.epam.ismyrnov.dto.Resource;
import com.epam.ismyrnov.dto.ResourceId;
import com.epam.ismyrnov.dto.SongMetadata;
import com.epam.ismyrnov.enums.Mp3Metadata;
import com.epam.ismyrnov.exception.ServiceException;
import com.epam.ismyrnov.exception.ValidationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceProcessorService {

  private final ResourceClient resourceClient;

  private final SongClient songClient;

  public ResourceId upload(Integer id) {
    Resource resource = resourceClient.getResource(id);
    log.info("Got file '{}' data", resource.getFileName());

    Long songId = createSong(resource.getBase64Data());
    return ResourceId.builder().id(songId).build();
  }

  //  @Retryable(maxAttempts = 2, backoff = @Backoff(delay = 3000))
  public Long createSong(String base64Data) {
    SongMetadata songMetadata = extractSongMetadata(base64Data);
    SongId songId = songClient.create(songMetadata);
    log.debug("Created song metadata with id {}", songId.getId());
    return songId.getId();
  }

  private SongMetadata extractSongMetadata(String base64Data) {
    byte[] data = Base64.getDecoder().decode(base64Data);
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
}
