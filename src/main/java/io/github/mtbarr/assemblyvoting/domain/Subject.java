package io.github.mtbarr.assemblyvoting.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Subject {

  private UUID id;
  private String title;
  private String description;

  private LocalDateTime votingEndTime;
  private boolean finalized;

  /**
   * Check if the subject is open to receive votes
   * @return true if the subject is open to receive votes, false otherwise
   */
  public boolean canReceiveVote() {
    return !finalized && LocalDateTime.now().isBefore(votingEndTime);
  }
}
