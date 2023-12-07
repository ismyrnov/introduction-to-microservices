package com.epam.ismyrnov.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.ismyrnov.dto.SongId;
import com.epam.ismyrnov.dto.SongMetadataCreateRequest;
import com.epam.ismyrnov.service.SongService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
  private final SongService songService;

  @PostMapping
  public SongId createSongMetadata(@RequestBody SongMetadataCreateRequest request) {
    return songService.createSong(request);
  }
}
