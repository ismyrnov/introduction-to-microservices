package com.epam.ismyrnov.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.epam.ismyrnov.dto.Resource;

@FeignClient(name = "resource-service", url = "${ismyrnov.services.resources.url}")
public interface ResourceClient {

  @GetMapping(path = "/resources/{id}")
  Resource getResource(@PathVariable Integer id);
}
