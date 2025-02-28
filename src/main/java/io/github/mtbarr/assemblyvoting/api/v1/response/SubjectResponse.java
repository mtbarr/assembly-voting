package io.github.mtbarr.assemblyvoting.api.v1.response;

import io.github.mtbarr.assemblyvoting.domain.Subject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record SubjectResponse(
  UUID subjectId,

  @Schema(description = "Título da pauta")
  String subjectTitle,

  @Schema(description = "Descrição da pauta")
  String subjectDescription
) {

  public SubjectResponse(Subject subject) {
    this(subject.getId(), subject.getTitle(), subject.getDescription());
  }
}
