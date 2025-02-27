package io.github.mtbarr.assemblyvoting.service;

import io.github.mtbarr.assemblyvoting.config.SessionServiceConfiguration;
import io.github.mtbarr.assemblyvoting.data.repository.SubjectRepository;
import io.github.mtbarr.assemblyvoting.domain.Subject;
import io.github.mtbarr.assemblyvoting.mapper.SubjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectService {

  private final SubjectRepository subjectRepository;
  private final SubjectMapper subjectMapper;


  public Subject getSubject(UUID id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public Subject registerNewSubject(String title, String description) {


    throw new UnsupportedOperationException("Not implemented yet");
  }

  public Page<Subject> listAllSubjects(Pageable pageable) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
