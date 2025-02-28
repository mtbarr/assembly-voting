package io.github.mtbarr.assemblyvoting.job;

import io.github.mtbarr.assemblyvoting.domain.Subject;
import io.github.mtbarr.assemblyvoting.service.SubjectService;
import io.github.mtbarr.assemblyvoting.service.VotingSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubjectSessionUpdateJob {

  private final SubjectService subjectService;

  private final VotingSessionService votingSessionService;

  @Scheduled(fixedRateString = "${session-settings.update-rate}", timeUnit = TimeUnit.SECONDS)
  public void updateVotingSessions() {

    List<Subject> subjects = subjectService.listAllExpiredAndNotFinalizedSubjects();
    if (!subjects.isEmpty()) {
      log.info("Checking for expired voting sessions: {}", subjects.size());
    }
    subjects.forEach(votingSessionService::finalizeSessionForSubject);
  }
}
