package com.epam.ismyrnov.service;

import org.springframework.stereotype.Service;

import com.epam.ismyrnov.dto.SongId;
import com.epam.ismyrnov.dto.SongMetadataCreateRequest;
import com.epam.ismyrnov.persistence.SongRepository;
import com.epam.ismyrnov.persistence.entity.SongEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongService {
  private final SongRepository songRepository;

  public SongId createSong(SongMetadataCreateRequest request) {
    SongEntity song = songRepository.save(SongEntity.builder()
        .name(request.getName())
        .album(request.getAlbum())
        .artist(request.getArtist())
        .year(request.getYear())
        .length(request.getLength())
        .resourceId(request.getResourceId())
        .build());
    return SongId.builder().id(song.getId()).build();
  }

}
