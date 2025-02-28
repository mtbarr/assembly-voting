package io.github.mtbarr.assemblyvoting.api.v1.controller;

import io.github.mtbarr.assemblyvoting.api.v1.request.CreateSubjectRequest;
import io.github.mtbarr.assemblyvoting.api.v1.request.StartVotingSessionRequest;
import io.github.mtbarr.assemblyvoting.api.v1.request.SubjectVoteRequest;
import io.github.mtbarr.assemblyvoting.api.v1.response.FinishedSubjectSessionResponse;
import io.github.mtbarr.assemblyvoting.api.v1.response.SubjectResponse;
import io.github.mtbarr.assemblyvoting.service.SubjectService;
import io.github.mtbarr.assemblyvoting.service.VotingSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/subjects")
@RequiredArgsConstructor
public class SubjectControllerImpl implements SubjectController {

  private final SubjectService subjectService;
  private final VotingSessionService votingSessionService;

  @Override
  @GetMapping
  public Page<SubjectResponse> listAllSubjects(@PageableDefault Pageable pageable) {
    return subjectService.listAllSubjects(pageable)
      .map(SubjectResponse::new);
  }

  @Override
  @GetMapping("/finished")
  public Page<FinishedSubjectSessionResponse> listAllFinishedSubjects(Pageable pageable) {
    return votingSessionService.listAllFinishedSubjects(pageable);
  }

  @Override
  @PostMapping
  public SubjectResponse createNewSubject(@RequestBody @Valid CreateSubjectRequest request) {
    return new SubjectResponse(subjectService.registerNewSubject(request.subjectTitle(), request.subjectDescription()));
  }

  @Override
  @PostMapping("/voting-session")
  @ResponseStatus(HttpStatus.CREATED)
  public void startVotingSession(@RequestBody @Valid StartVotingSessionRequest request) {
    votingSessionService.openSessionForSubject(request.subjectId(), request.duration());
  }

  @Override
  @PostMapping("/vote")
  @ResponseStatus(HttpStatus.CREATED)
  public void voteOnSubject(@RequestBody SubjectVoteRequest request) {
    votingSessionService.voteOnSubject(request.subjectId(), request.associateId(), request.voteType());
  }
}
