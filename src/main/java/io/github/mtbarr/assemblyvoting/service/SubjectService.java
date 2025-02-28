package io.github.mtbarr.assemblyvoting.service;

import io.github.mtbarr.assemblyvoting.data.repository.SubjectRepository;
import io.github.mtbarr.assemblyvoting.domain.Subject;
import io.github.mtbarr.assemblyvoting.mapper.SubjectMapper;
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
public class SubjectService {

  private final SubjectRepository subjectRepository;
  private final SubjectMapper subjectMapper;


  /**
   * Retrieves a subject by its ID, caching the result.
   *
   * @param id the UUID of the subject to retrieve
   * @return the retrieved Subject
   * @throws RuntimeException if the subject is not found
   */
  public Subject getSubject(UUID id) {
    log.info("Fetching subject with id: {}", id);
    return subjectRepository.findById(id)
      .map(subjectMapper::toSubject)
      .orElseThrow(() -> new RuntimeException("Subject not found"));
  }


  /**
   * Updates a subject and evicts the cache for the subject.
   *
   * @param subjectToUpdate the subject to update
   * @return the updated Subject
   */
  public void updateSubject(Subject subjectToUpdate) {
    subjectRepository.save(subjectMapper.toEntity(subjectToUpdate));
    log.info("Successful updated subject with id: {}", subjectToUpdate.getId());
  }

  /**
   * Registers a new subject and evicts the cache for the subject.
   *
   * @param title the title of the new subject
   * @param description the description of the new subject
   * @return the registered Subject
   */
  public Subject registerNewSubject(String title, String description) {
    log.info("Registering new subject with title: {}", title);
    var newSubject = Subject.builder()
      .title(title)
      .description(description)
      .build();

    return subjectMapper.toSubject(subjectRepository.save(subjectMapper.toEntity(newSubject)));
  }

  /**
   * Lists all subjects with pagination, caching the result.
   *
   * @param pageable the pagination information
   * @return a paginated list of subjects
   */
  public Page<Subject> listAllSubjects(Pageable pageable) {
    log.info("Fetching all subjects with pagination: {}", pageable);
    return subjectRepository.findAll(pageable)
      .map(subjectMapper::toSubject);
  }


  /**
   * Lists all subjects that have expired and are not finalized.
   *
   * @return a list of subjects
   */
  public List<Subject> listAllExpiredAndNotFinalizedSubjects() {
    return subjectRepository.findAllOpenAndNotFinalized(LocalDateTime.now())
      .stream()
      .map(subjectMapper::toSubject)
      .toList();
  }

  /**
   * Lists all finished subjects with pagination.
   *
   * @param pageable the pagination information
   * @return a paginated list of finished subjects
   */
  public Page<Subject> listAllFinishedSubjects(Pageable pageable) {
    log.info("Fetching all finished subjects with pagination: {}", pageable);
    return subjectRepository.findAllFinalized(pageable)
      .map(subjectMapper::toSubject);
  }
}