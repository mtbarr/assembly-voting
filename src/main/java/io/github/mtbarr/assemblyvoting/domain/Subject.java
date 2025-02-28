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
   * Checks if the voting session is open.
   *
   * @return true if the session is open, false otherwise
   */
  public boolean isSessionCreated() {
    return !finalized && votingEndTime != null;
  }

  /**
   * Checks if the voting session has expired.
   *
   * @return true if the session has expired, false otherwise
   */
  public boolean isSessionExpired() {
    return votingEndTime != null && LocalDateTime.now().isAfter(votingEndTime);
  }

  /**
   * Checks if the voting session is running.
   *
   * @return true if the session is running, false otherwise
   */
  public boolean isSessionRunning() {
    return votingEndTime != null && !finalized && LocalDateTime.now().isBefore(votingEndTime);
  }

}
