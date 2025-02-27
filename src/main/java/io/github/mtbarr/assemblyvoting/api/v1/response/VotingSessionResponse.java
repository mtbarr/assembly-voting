package io.github.mtbarr.assemblyvoting.api.v1.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record VotingSessionResponse(
  UUID subjectId,
  LocalDateTime votingEndTime
) {
}
