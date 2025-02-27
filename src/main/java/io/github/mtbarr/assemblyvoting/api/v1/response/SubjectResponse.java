package io.github.mtbarr.assemblyvoting.api.v1.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record SubjectResponse(
  UUID subjectId,

  @Schema(description = "Título da pauta")
  String subjectTitle,

  @Schema(description = "Descrição da pauta")
  String subjectDescription
) {
}
