package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class SessionAlreadyOpenException extends CommonException {
  public SessionAlreadyOpenException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
