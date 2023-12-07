package com.epam.ismyrnov.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.ismyrnov.dto.SongId;
import com.epam.ismyrnov.dto.Song;
import com.epam.ismyrnov.dto.SongIds;
import com.epam.ismyrnov.service.SongService;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {
  private final SongService songService;

  @PostMapping
  public SongId createSongMetadata(@Valid @RequestBody Song request) {
    return songService.createSong(request);
  }

  @GetMapping("/{id}")
  public Song getSong(@PathVariable Integer id) {
    return songService.getSong(id);
  }

  @DeleteMapping
  public SongIds delete(@RequestParam @Size(min = 1, max = 200) List<Long> ids) {
    return songService.delete(ids);
  }
}
