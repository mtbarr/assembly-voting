package io.github.mtbarr.assemblyvoting.api.v1.response;

import io.github.mtbarr.assemblyvoting.domain.SessionResultType;

import java.util.UUID;

public record FinishedSubjectSessionResponse(
  UUID subjectId,
  String subjectTitle,
  String subjectDescription,
  SessionResultType sessionResultType,
  long yesVotes,
  long noVotes,
  long totalVotes
) {

  public FinishedSubjectSessionResponse(
    UUID subjectId,
    String subjectTitle,
    String subjectDescription,
    SessionResultType sessionResultType,
    long yesVotes,
    long noVotes
  ) {
    this(subjectId, subjectTitle, subjectDescription, sessionResultType, yesVotes, noVotes, yesVotes + noVotes);
  }
}
