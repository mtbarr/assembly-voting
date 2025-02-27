package io.github.mtbarr.assemblyvoting.api.v1.controller;

import io.github.mtbarr.assemblyvoting.api.v1.request.CreateAssociateRequest;
import io.github.mtbarr.assemblyvoting.api.v1.response.AssociateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;


@Tag(name = "API de associados", description = "Operações relacionadas a associados")
public interface AssociateController {

  @Operation(summary = "Listar todos os associados", description = "Lista todos os associados cadastrados no sistema")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Associados listados com sucesso"),
  })
  Page<AssociateResponse> listAllAssociates();

  @Operation(summary = "Registrar um novo associado", description = "Registra um novo associado no sistema")
  @Parameter(name = "request", description = "Dados do associado a ser registrado", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Associado registrado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    @ApiResponse(responseCode = "409", description = "Associado já registrado")
  })
  AssociateResponse registerNewAssociate(CreateAssociateRequest request);
}
