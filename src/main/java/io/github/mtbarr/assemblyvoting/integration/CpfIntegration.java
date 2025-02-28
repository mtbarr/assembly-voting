package io.github.mtbarr.assemblyvoting.integration;

import io.github.mtbarr.assemblyvoting.domain.AssociateVotingEligibility;
import io.github.mtbarr.assemblyvoting.domain.VotingStatus;
import io.github.mtbarr.assemblyvoting.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CpfIntegration {

  @Value("${session-settings.endpoint-url}")
  private String endpointUrl;

  private final RestClient.Builder restClient;

  /**
   * Checks if the given CPF is valid and eligible to vote.
   *
   * @param associateCpf the CPF of the associate to check
   * @return true if the CPF is valid and the associate is eligible to vote, false otherwise
   * @throws ResourceNotFoundException if the CPF is not found
   */
  public boolean isValidCpf(String associateCpf) {
    AssociateVotingEligibility eligibility = restClient.build()
      .get()
      .uri(endpointUrl, associateCpf)
      .retrieve()
      .body(AssociateVotingEligibility.class);

    return eligibility != null && eligibility.status() == VotingStatus.ABLE_TO_VOTE;
  }
}
