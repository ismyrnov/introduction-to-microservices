package com.epam.ismyrnov.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongIds {
  private final List<Long> ids;
}
