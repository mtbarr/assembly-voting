package io.github.mtbarr.assemblyvoting.messaging;

import io.github.mtbarr.assemblyvoting.domain.SessionResultType;

import java.util.UUID;

public record VotingSessionResult(
  UUID subjectId,
  String subjectTitle,
  String subjectDescription,
  SessionResultType type,
  long yesVotes,
  long noVotes,
  long totalVotes
) {

  public VotingSessionResult(UUID subjectId, String subjectTitle, String subjectDescription, SessionResultType type, long yesVotes, long noVotes) {
    this(subjectId, subjectTitle, subjectDescription, type, yesVotes, noVotes, yesVotes + noVotes);
  }
}
