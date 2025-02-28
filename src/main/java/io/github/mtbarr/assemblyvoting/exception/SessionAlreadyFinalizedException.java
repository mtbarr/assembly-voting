package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class SessionAlreadyFinalizedException extends CommonException {
  public SessionAlreadyFinalizedException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
