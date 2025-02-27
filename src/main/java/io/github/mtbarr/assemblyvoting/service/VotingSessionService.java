package io.github.mtbarr.assemblyvoting.service;

import io.github.mtbarr.assemblyvoting.domain.VoteType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VotingSessionService {

  public void openSessionForSubject(UUID subjectId, final LocalDateTime time) {
    // todo
  }

  public void voteOnSubject(UUID subjectId, UUID associateId, VoteType type) {
    // todo
  }

  public void tailSessionForSubject(UUID subjectId) {
    // todo
  }
}
