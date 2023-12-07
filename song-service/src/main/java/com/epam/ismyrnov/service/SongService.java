package com.epam.ismyrnov.service;

import org.springframework.stereotype.Service;

import com.epam.ismyrnov.dto.SongId;
import com.epam.ismyrnov.dto.Song;
import com.epam.ismyrnov.exception.ResourceNotFoundException;
import com.epam.ismyrnov.persistence.SongRepository;
import com.epam.ismyrnov.persistence.entity.SongEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongService {
  private final SongRepository songRepository;

  public SongId createSong(Song request) {
    SongEntity song =
        songRepository.save(
            SongEntity.builder()
                .name(request.getName())
                .album(request.getAlbum())
                .artist(request.getArtist())
                .year(request.getYear())
                .length(request.getLength())
                .resourceId(request.getResourceId())
                .build());
    return SongId.builder().id(song.getId()).build();
  }

  public Song getSong(Integer id) {
    return songRepository
        .findById(Long.valueOf(id))
        .map(
            song ->
                Song.builder()
                    .year(song.getYear())
                    .name(song.getName())
                    .resourceId(song.getResourceId())
                    .length(song.getLength())
                    .album(song.getAlbum())
                    .artist(song.getArtist())
                    .build())
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "The song metadata with the specified id does not exist"));
  }
}
