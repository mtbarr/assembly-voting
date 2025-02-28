package io.github.mtbarr.assemblyvoting.service;

import io.github.mtbarr.assemblyvoting.api.v1.response.FinishedSubjectSessionResponse;
import io.github.mtbarr.assemblyvoting.config.SessionServiceConfiguration;
import io.github.mtbarr.assemblyvoting.data.entity.VoteEntity;
import io.github.mtbarr.assemblyvoting.data.projection.SessionResultProjection;
import io.github.mtbarr.assemblyvoting.data.repository.VoteRepository;
import io.github.mtbarr.assemblyvoting.domain.SessionResultType;
import io.github.mtbarr.assemblyvoting.domain.Subject;
import io.github.mtbarr.assemblyvoting.domain.Vote;
import io.github.mtbarr.assemblyvoting.domain.VoteType;
import io.github.mtbarr.assemblyvoting.exception.*;
import io.github.mtbarr.assemblyvoting.mapper.VoteMapper;
import io.github.mtbarr.assemblyvoting.messaging.VotingSessionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VotingSessionService {

  private final SessionServiceConfiguration sessionServiceConfiguration;

  private final SubjectService subjectService;

  private final AssociateService associateService;

  private final MessageService messageService;

  private final VoteRepository voteRepository;
  private final VoteMapper voteMapper;

  //{
  //    "subjectId": "9ea3b13f-0bcb-458c-8b5d-6406dfd72394",
  //    "duration": "4"
  //}
  public void openSessionForSubject(UUID subjectId, int durationInSeconds) {
    var time = LocalDateTime.now().plusSeconds(durationInSeconds);
    log.info("Opening session for subject with id: {} until: {}", subjectId, time);

    var timeNow = LocalDateTime.now();
    if (time.isBefore(timeNow)) {
      throw new InvalidSessionTimeException("The session end time must be in the future");
    }

    var subject = subjectService.getSubject(subjectId);
    if (subject.isSessionCreated()) {
      throw new SessionAlreadyOpenException("The session for this subject is already open");
    }

    log.info("Updating subject with id: {}", subjectId);
    subject.setVotingEndTime(time);
    subjectService.updateSubject(subject);

    log.info("Session for subject {} opened until {}", subjectId, time);
  }

  public Vote voteOnSubject(UUID subjectId, UUID associateId, VoteType voteType) {
    log.info("Voting on subject with id: {} for associate with id: {} and type: {}", subjectId, associateId, voteType);

    var subject = subjectService.getSubject(subjectId);

    log.info("Validating voting session state for subject with id: {}", subjectId);
    validateAssociateVoteToSubject(subjectId, associateId, subject);

    var associate = associateService.getAssociate(associateId);
    log.info("Found associate with id: {}", associateId);

    var vote = Vote.builder()
      .associate(associate)
      .subject(subject)
      .voteType(voteType)
      .build();

    log.info("Saving vote: {}", vote);
    VoteEntity saved = voteRepository.save(voteMapper.toEntity(vote));
    log.info("Vote saved: {}", vote);
    return voteMapper.toVote(saved);
  }


  public void finalizeSessionForSubject(Subject subject) {
    log.info("Finalizing session for subject with id: {}", subject.getId());
    log.info("Validating subject session state for subject with id: {}", subject.getId());
    validateSubjectSessionState(subject);

    log.info("Finalizing subject with id: {}", subject.getId());
    subject.setFinalized(true);
    subjectService.updateSubject(subject);

    log.info("Computing session result for subject with id: {}", subject.getId());
    var sessionResult = computeResult(subject);

    log.info("Publishing session result message for subject with id: {}", subject.getId());
    /*
     * Tarefa Bônus 2 - Mensageria e filas
     * O resultado da votação precisa ser informado para o restante da plataforma,
     * isso deve ser feito preferencialmente através de mensageria.
     * Quando a sessão de votação fechar, poste uma mensagem com o resultado da votação.
     */
    messageService.publishSessionResult(sessionResult);
    log.info("Session for subject with id: {} finalized", sessionResult);
  }

  public Page<FinishedSubjectSessionResponse> listAllFinishedSubjects(Pageable pageable) {
    log.info("Fetching all finished subjects with pagination: {}", pageable);
    return subjectService.listAllFinishedSubjects(pageable)
      .map(this::computeFinalizedResponse);
  }


  private FinishedSubjectSessionResponse computeFinalizedResponse(Subject subject) {
    var yesVotes = voteRepository.countByVoteTypeAndSubjectId(VoteType.YES, subject.getId());
    var noVotes = voteRepository.countByVoteTypeAndSubjectId(VoteType.NO, subject.getId());
    return new FinishedSubjectSessionResponse(
      subject.getId(),
      subject.getTitle(),
      subject.getDescription(),
      calculateSessionResultType(yesVotes, noVotes),
      yesVotes,
      noVotes
    );
  }

  private VotingSessionResult computeResult(Subject subject) {
    var yesVotes = voteRepository.countByVoteTypeAndSubjectId(VoteType.YES, subject.getId());
    var noVotes = voteRepository.countByVoteTypeAndSubjectId(VoteType.NO, subject.getId());
    log.info("Yes {} No {}", yesVotes, noVotes);

    return new VotingSessionResult(
      subject.getId(),
      subject.getTitle(),
      subject.getDescription(),
      calculateSessionResultType(yesVotes, noVotes),
      yesVotes,
      noVotes
    );
  }

  private SessionResultType calculateSessionResultType(long yesVotes, long noVotes) {
    if (yesVotes > noVotes) {
      return SessionResultType.APPROVED;
    } else if (noVotes > yesVotes) {
      return SessionResultType.REJECTED;
    } else {
      return SessionResultType.TIED;
    }
  }

  private void validateAssociateVoteToSubject(UUID subjectId, UUID associateId, Subject subject) {
    if (!subject.isSessionCreated()) {
      throw new SessionNotOpenException("The session for this subject is not open");
    }

    if (subject.isSessionExpired()) {
      throw new SessionNotOpenException("The session for this subject is expired");
    }

    if (voteRepository.existsByAssociateIdAndSubjectId(associateId, subjectId)) {
      throw new AssociateAlreadyVotedException("The associate has already voted on this subject");
    }
  }

  private void validateSubjectSessionState(Subject subject) {
    if (!subject.isSessionCreated()) {
      throw new SessionNotOpenException("The session for this subject is not open");
    }

    if (subject.isFinalized()) {
      throw new SessionAlreadyFinalizedException("The session for this subject is already finalized");
    }
  }
}
