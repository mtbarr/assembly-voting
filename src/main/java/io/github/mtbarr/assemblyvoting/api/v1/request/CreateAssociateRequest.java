package io.github.mtbarr.assemblyvoting.api.v1.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateAssociateRequest(
  @Schema(description = "CPF do associado", example = "612.826.120-90")
  @NotNull
  String cpf
) {
}
