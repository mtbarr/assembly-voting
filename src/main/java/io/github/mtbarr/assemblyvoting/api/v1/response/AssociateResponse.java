package io.github.mtbarr.assemblyvoting.api.v1.response;


import io.github.mtbarr.assemblyvoting.domain.Associate;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record AssociateResponse(
  @Schema(description = "ID do associado criado", example = "123e4567-e89b-12d3-a456-426614174000")
  UUID associateId,

  @Schema(description = "CPF do associado criado", example = "612.826.120-90")
  String cpf
) {

  public AssociateResponse(Associate associate) {
    this(associate.getId(), associate.getCpf());
  }
}

