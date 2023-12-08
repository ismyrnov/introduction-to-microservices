package com.epam.ismyrnov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.ismyrnov.client.dto.SongId;
import com.epam.ismyrnov.dto.SongMetadata;

@FeignClient(name = "songs-service", url = "${ismyrnov.services.songs.url}")
public interface SongClient {

  @PostMapping(path = "/songs")
  SongId create(@RequestBody SongMetadata request);
}