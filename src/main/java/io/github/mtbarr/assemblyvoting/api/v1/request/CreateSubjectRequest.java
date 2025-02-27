package io.github.mtbarr.assemblyvoting.api.v1.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateSubjectRequest(
  @Schema(description = "Título da pauta")
  String subjectTitle,

  @Schema(description = "Descrição da pauta")
  String subjectDescription
){}
