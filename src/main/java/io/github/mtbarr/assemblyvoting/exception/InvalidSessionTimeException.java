package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class InvalidSessionTimeException extends CommonException {
  public InvalidSessionTimeException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
  }
}
