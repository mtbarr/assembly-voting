package io.github.mtbarr.assemblyvoting.api.v1.controller;


import io.github.mtbarr.assemblyvoting.api.v1.request.CreateSubjectRequest;
import io.github.mtbarr.assemblyvoting.api.v1.request.StartVotingSessionRequest;
import io.github.mtbarr.assemblyvoting.api.v1.request.SubjectVoteRequest;
import io.github.mtbarr.assemblyvoting.api.v1.response.FinishedSubjectSessionResponse;
import io.github.mtbarr.assemblyvoting.api.v1.response.SubjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "API de pautas", description = "Operações relacionadas a pautas")
public interface SubjectController {


  @Operation(summary = "Listar todas as pautas", description = "Lista todas as pautas cadastradas no sistema")
  @Parameter(name = "pageable", description = "Parâmetros de paginação", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pautas listadas com sucesso"),
  })
  Page<SubjectResponse> listAllSubjects(@PageableDefault Pageable pageable);


  @Operation(summary = "Listar todas as pautas finalizadas", description = "Lista todas as pautas finalizadas no sistema")
  @Parameter(name = "pageable", description = "Parâmetros de paginação", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pautas finalizadas listadas com sucesso"),
  })
  Page<FinishedSubjectSessionResponse> listAllFinishedSubjects(@PageableDefault Pageable pageable);

  @Operation(summary = "Criar uma nova pauta", description = "Cria uma nova pauta no sistema")
  @Parameter(name = "request", description = "Dados da pauta a ser criada", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
  })
  SubjectResponse createNewSubject(@RequestBody CreateSubjectRequest request);

  @Operation(summary = "Iniciar sessão de votação para uma pauta", description = "Inicia uma sessão de votação para uma pauta")
  @Parameter(name = "request", description = "Dados da sessão de votação a ser iniciada", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Sessão de votação iniciada com sucesso"),
    @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
  })
  void startVotingSession(@RequestBody StartVotingSessionRequest request);


  @Operation(summary = "Votar em uma pauta", description = "Vota em uma pauta")
  @Parameter(name = "request", description = "Dados do voto a ser registrado", required = true)
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
    @ApiResponse(responseCode = "409", description = "Sessão de votação encerrada")
  })
  void voteOnSubject(@RequestBody SubjectVoteRequest request);
}
