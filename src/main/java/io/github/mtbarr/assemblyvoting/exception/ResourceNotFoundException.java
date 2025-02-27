package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CommonException {

  public ResourceNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
