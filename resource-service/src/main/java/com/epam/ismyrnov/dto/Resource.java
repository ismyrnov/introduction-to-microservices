package com.epam.ismyrnov.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = Resource.FileBuilder.class)
public class Resource {

  @NotBlank String base64Data;

  @JsonPOJOBuilder(withPrefix = "")
  public static class FileBuilder {}
}
