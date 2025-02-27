package io.github.mtbarr.assemblyvoting.api.v1.controller;

import io.github.mtbarr.assemblyvoting.api.v1.request.CreateSubjectRequest;
import io.github.mtbarr.assemblyvoting.api.v1.request.StartVotingSessionRequest;
import io.github.mtbarr.assemblyvoting.api.v1.request.SubjectVoteRequest;
import io.github.mtbarr.assemblyvoting.api.v1.response.VotingSessionResponse;
import io.github.mtbarr.assemblyvoting.api.v1.response.SubjectResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/subjects")
public class SubjectControllerImpl implements SubjectController {

  @Override
  @GetMapping
  public Page<SubjectResponse> listAllSubjects(@PageableDefault Pageable pageable) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  @PostMapping
  public SubjectResponse createNewSubject(@RequestBody @Valid CreateSubjectRequest request) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  @PostMapping("/voting-session")
  @ResponseStatus(HttpStatus.CREATED)
  public VotingSessionResponse startVotingSession(@RequestBody @Valid StartVotingSessionRequest request) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  @PostMapping("/vote")
  @ResponseStatus(HttpStatus.CREATED)
  public void voteOnSubject(SubjectVoteRequest request) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
