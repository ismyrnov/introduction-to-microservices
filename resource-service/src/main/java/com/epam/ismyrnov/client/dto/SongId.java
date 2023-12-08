package com.epam.ismyrnov.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@JsonDeserialize(builder = SongId.SongIdBuilder.class)
public class SongId {

  private final Long id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SongIdBuilder {}
}
