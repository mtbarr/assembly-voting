package io.github.mtbarr.assemblyvoting.data.projection;

import io.github.mtbarr.assemblyvoting.domain.VoteType;

public interface VoteCountProjection {
  VoteType getVoteType();

  long getTotalVotes();
}
