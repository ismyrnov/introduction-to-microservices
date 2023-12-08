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

import com.epam.ismyrnov.dto.File;
import com.epam.ismyrnov.dto.FileId;
import com.epam.ismyrnov.service.ResourceService;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
  private final ResourceService resourceService;

  @PostMapping
  public FileId upload(@RequestBody File request) {
    return resourceService.upload(request);
  }
  //
  //  @GetMapping("/{id}")
  //  public Song getSong(@PathVariable Integer id) {
  //    return resourceService.getSong(id);
  //  }
  //
  //  @DeleteMapping
  //  public SongIds delete(@RequestParam @Size(min = 1, max = 200) List<Long> ids) {
  //    return resourceService.delete(ids);
  //  }
}
