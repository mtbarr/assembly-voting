package io.github.mtbarr.assemblyvoting.api.v1.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record StartVotingSessionRequest(
  @Schema(description = "ID da pauta", example = "123e4567-e89b-12d3-a456-426614174000")
  LocalDate votingEndTime
) {
}
