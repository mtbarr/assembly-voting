package io.github.mtbarr.assemblyvoting.api.v1.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record StartVotingSessionRequest(
  @Schema(description = "ID da pauta", example = "123e4567-e89b-12d3-a456-426614174000")
  UUID subjectId,

  @Schema(description = "Duração da sessão de votação em minutos", example = "60")
  int duration
) {
}
