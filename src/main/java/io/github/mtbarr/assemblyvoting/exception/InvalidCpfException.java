package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class InvalidCpfException extends CommonException {
  public InvalidCpfException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}
