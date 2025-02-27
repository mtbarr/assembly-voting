package io.github.mtbarr.assemblyvoting.api.v1.request;

import io.github.mtbarr.assemblyvoting.domain.VoteType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record SubjectVoteRequest(
  @Schema(description = "ID do associado", example = "123e4567-e89b-12d3-a456-426614174000")
  UUID associateId,

  @Schema(description = "ID da pauta", example = "123e4567-e89b-12d3-a456-426614174000")
  UUID subjectId,

  @Schema(description = "Tipo do voto", example = "YES")
  VoteType voteType
) {
}
