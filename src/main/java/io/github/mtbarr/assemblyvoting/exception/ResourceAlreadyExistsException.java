package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends CommonException {

  public ResourceAlreadyExistsException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
