package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class AssociateAlreadyExistsException extends CommonException {

  public AssociateAlreadyExistsException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
