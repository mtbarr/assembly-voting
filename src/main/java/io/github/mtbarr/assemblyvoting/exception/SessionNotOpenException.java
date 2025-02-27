package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class SessionNotOpenException extends CommonException {
  public SessionNotOpenException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
