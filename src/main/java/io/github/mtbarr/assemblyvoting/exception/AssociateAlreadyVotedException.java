package io.github.mtbarr.assemblyvoting.exception;

import org.springframework.http.HttpStatus;

public class AssociateAlreadyVotedException extends CommonException {
  public AssociateAlreadyVotedException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
