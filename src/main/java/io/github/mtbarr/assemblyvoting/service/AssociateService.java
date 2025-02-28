package io.github.mtbarr.assemblyvoting.service;

import io.github.mtbarr.assemblyvoting.config.SessionServiceConfiguration;
import io.github.mtbarr.assemblyvoting.data.repository.AssociateRepository;
import io.github.mtbarr.assemblyvoting.domain.Associate;
import io.github.mtbarr.assemblyvoting.exception.InvalidCpfException;
import io.github.mtbarr.assemblyvoting.exception.ResourceNotFoundException;
import io.github.mtbarr.assemblyvoting.integration.CpfIntegration;
import io.github.mtbarr.assemblyvoting.mapper.AssociateMapper;
import io.github.mtbarr.assemblyvoting.util.CpfUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for managing associates.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AssociateService {

  private final AssociateRepository associateRepository;
  private final AssociateMapper associateMapper;
  private final SessionServiceConfiguration sessionServiceConfiguration;
  private final CpfIntegration cpfIntegration;


  /**
   * Retrieves all associates with pagination, caching the result.
   *
   * @param pageable the pagination information
   * @return the retrieved Page of Associates
   */
  public Page<Associate> getAllAssociates(Pageable pageable) {
    log.info("Fetching all associates with pagination: {}", pageable);
    return associateRepository.findAll(pageable)
      .map(associateMapper::toAssociate);
  }

  /**
   * Retrieves an associate by its ID, caching the result.
   *
   * @param id the UUID of the associate to retrieve
   * @return the retrieved Associate
   * @throws ResourceNotFoundException if the associate is not found
   */
  public Associate getAssociate(UUID id) {
    log.info("Fetching associate with id: {}", id);
    return associateRepository.findById(id)
      .map(associateMapper::toAssociate)
      .orElseThrow(() -> new ResourceNotFoundException("Associate not found with id: " + id));
  }

  /**
   * Registers a new associate and evicts the cache for the associate.
   *
   * @param associateCpf the CPF of the new associate
   * @return the registered Associate
   * @throws InvalidCpfException if the CPF is invalid
   * @throws ResourceNotFoundException if the associate is already registered
   */
  public Associate registerNewAssociate(String associateCpf) {
    log.info("Registering new associate with CPF: {}", associateCpf);

    log.info("Formatting CPF: {}", associateCpf);
    var formattedCpf = CpfUtil.formatCpf(associateCpf);
    log.info("Formatted CPF: {}", formattedCpf);

    log.info("Validating associate CPF: {}", formattedCpf);
    validateAssociateCpf(formattedCpf);
    log.info("Associate CPF is valid: {}", formattedCpf);


    log.info("Creating associate with CPF: {}", formattedCpf);
    var associate = Associate.builder()
      .cpf(formattedCpf)
      .build();
    log.info("Associate created successfully with CPF: {}", formattedCpf);

    log.info("Saving associate: {}", associate);
    Associate savedAssociate = associateMapper.toAssociate(associateRepository.save(associateMapper.toEntity(associate)));
    log.info("Associate registered successfully with id: {}", savedAssociate.getId());
    return savedAssociate;
  }


  private void validateAssociateCpf(String associateCpf) {
    /*
     * Tarefa Bônus 1 - Integração com sistemas externos
     * - Integrar com um sistema que verifique, a partir do CPF do associado, se ele pode votar.
     * - GET https://user-info.herokuapp.com/users/{cpf}
     * - Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos;
     * - Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação.
     */

    log.info("Validating CPF syntax: {}", associateCpf);
    boolean cpfValidationEnabled = sessionServiceConfiguration.isCpfValidationEnabled();
    if (cpfValidationEnabled) {
      if (!CpfUtil.isCpf(associateCpf)) {
        log.error("CPF Syntax is Invalid: {}", associateCpf);
        throw new InvalidCpfException("CPF Syntax is Invalid: " + associateCpf);
      }

      log.info("Validating CPF: {}", associateCpf);
      if (!cpfIntegration.isValidCpf(associateCpf)) {
        log.error("Associate has a invalid CPF: {}", associateCpf);
        throw new InvalidCpfException("Associate has a invalid CPF: " + associateCpf);
      }
    }

    log.info("Checking if associate is already registered with CPF: {}", associateCpf);
    if (associateRepository.existsByCpf(associateCpf)) {
      log.error("Associate already registered with CPF: {}", associateCpf);
      throw new ResourceNotFoundException("Associate already registered with CPF: " + associateCpf);
    }
  }
}