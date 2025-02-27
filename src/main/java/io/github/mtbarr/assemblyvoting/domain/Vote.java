package io.github.mtbarr.assemblyvoting.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Vote {

  private UUID id;
  private Subject subject;
  private Associate associate;
  private VoteType voteType;
}
