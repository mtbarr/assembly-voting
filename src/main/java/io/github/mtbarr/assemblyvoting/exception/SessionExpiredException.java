package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class SessionExpiredException extends CommonException {
  public SessionExpiredException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
