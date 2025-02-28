package io.github.mtbarr.assemblyvoting.api.v1.response;

import java.util.UUID;

public record FinishedSubjectSessionResponse(
  UUID subjectId,
  String subjectTitle,
  String subjectDescription,
  Long totalVotes,
  Long totalYesVotes,
  Long totalNoVotes
) {
}
