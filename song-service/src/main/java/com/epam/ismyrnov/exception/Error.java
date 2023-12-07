package com.epam.ismyrnov.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
  private final String errorMessage;
}
