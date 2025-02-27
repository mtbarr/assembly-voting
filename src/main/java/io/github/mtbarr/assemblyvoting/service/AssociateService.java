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
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssociateService {

  private final AssociateRepository associateRepository;
  private final AssociateMapper associateMapper;

  private final SessionServiceConfiguration sessionServiceConfiguration;
  private final CpfIntegration cpfIntegration;


  public Associate getAssociate(UUID id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public Associate registerNewAssociate(String associateCpf) {
    if (sessionServiceConfiguration.isCpfValidationEnabled() && !CpfUtil.isValidCpf(associateCpf)) {
      throw new InvalidCpfException("Invalid CPF: " + associateCpf);
    }

    if (!cpfIntegration.isValidCpf(associateCpf)) {
      throw new InvalidCpfException("Invalid CPF: " + associateCpf);
    }

    if (associateRepository.existsByCpf(associateCpf)) {
      throw new ResourceNotFoundException("Associate already registered with CPF: " + associateCpf);
    }

    var associate = Associate.builder()
      .cpf(associateCpf)
      .build();

    return associateMapper.toAssociate(associateRepository.save(associateMapper.toEntity(associate)));
  }
}
