package com.epam.ismyrnov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.ismyrnov.client.dto.SongId;
import com.epam.ismyrnov.dto.SongMetadata;

@FeignClient(name = "song-service", url = "${ismyrnov.services.songs.url}")
// @FeignClient(name = "song-service") // for discovery mode
public interface SongClient {

  @PostMapping(path = "/songs")
  SongId create(@RequestBody SongMetadata request);
}
