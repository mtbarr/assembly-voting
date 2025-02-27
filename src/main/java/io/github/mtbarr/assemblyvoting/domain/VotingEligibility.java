package io.github.mtbarr.assemblyvoting.domain;

public record VotingEligibility(VotingStatus status) {

  public boolean isAbleToVote() {
    return status == VotingStatus.ABLE_TO_VOTE;
  }
}
