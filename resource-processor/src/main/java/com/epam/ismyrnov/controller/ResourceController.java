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

import com.epam.ismyrnov.dto.Resource;
import com.epam.ismyrnov.dto.ResourceId;
import com.epam.ismyrnov.dto.ResourceIds;
import com.epam.ismyrnov.service.ResourceService;

import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
  private final ResourceService resourceService;

  @PostMapping
  public ResourceId upload(@RequestBody Resource request) {
    return resourceService.upload(request);
  }

  @GetMapping("/{id}")
  public Resource getResource(@PathVariable Integer id) {
    return resourceService.getResource(id);
  }

  @DeleteMapping
  public ResourceIds delete(@RequestParam @Size(min = 1, max = 200) List<Long> ids) {
    return resourceService.delete(ids);
  }
}
