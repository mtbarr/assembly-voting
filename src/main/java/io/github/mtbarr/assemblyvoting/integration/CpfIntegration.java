package io.github.mtbarr.assemblyvoting.integration;

import io.github.mtbarr.assemblyvoting.config.SessionServiceConfiguration;
import io.github.mtbarr.assemblyvoting.domain.VotingEligibility;
import io.github.mtbarr.assemblyvoting.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CpfIntegration {

  private final SessionServiceConfiguration sessionServiceConfiguration;
  private final RestTemplate restTemplate;

  /**
   * Checks if the given CPF is valid and eligible to vote.
   *
   * @param associateCpf the CPF of the associate to check
   * @return true if the CPF is valid and the associate is eligible to vote, false otherwise
   * @throws ResourceNotFoundException if the CPF is not found
   */
  public boolean isValidCpf(String associateCpf) {

  }
}
