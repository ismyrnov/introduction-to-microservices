package com.epam.ismyrnov.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.ismyrnov.dto.SongMetadataCreateRequest;

@RestController
@RequestMapping("/songs")
public class SongController {

  @PostMapping
  public String createSongMetadata(@RequestBody SongMetadataCreateRequest request) {
    return "id-123";
  }

}
