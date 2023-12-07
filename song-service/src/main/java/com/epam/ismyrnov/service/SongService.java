package com.epam.ismyrnov.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.ismyrnov.dto.SongId;
import com.epam.ismyrnov.dto.Song;
import com.epam.ismyrnov.dto.SongIds;
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

  @Transactional
  public SongIds delete(List<Long> ids) {
    List<SongEntity> songs = songRepository.findAllById(ids);
    List<Long> existingIds = songs.stream().map(SongEntity::getId).collect(Collectors.toList());
    songRepository.deleteAllById(existingIds);
    return SongIds.builder().ids(existingIds).build();
  }
}
