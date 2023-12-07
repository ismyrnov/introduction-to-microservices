package com.epam.ismyrnov.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ResourceNotFoundException.class})
  protected ResponseEntity<Error> handleResourceNotFoundException(ResourceNotFoundException ex) {
    return ResponseEntity.status(404).body(Error.builder().errorMessage(ex.getMessage()).build());
  }

  @ExceptionHandler(value = {RuntimeException.class})
  protected ResponseEntity<Error> handleDefaultException(RuntimeException ex) {
    log.error("An internal server error has occurred: ", ex);
    return ResponseEntity.status(500)
        .body(
            Error.builder()
                .errorMessage(
                    String.format("An internal server error has occurred: %s", ex.getMessage()))
                .build());
  }
}
