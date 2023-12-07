package com.epam.ismyrnov.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Song {
  @NotBlank private final String name;
  @NotBlank private final String artist;
  @NotBlank private final String album;
  @NotBlank private final String length;
  @NotBlank private final String resourceId;
  @NotBlank private final String year;
}
