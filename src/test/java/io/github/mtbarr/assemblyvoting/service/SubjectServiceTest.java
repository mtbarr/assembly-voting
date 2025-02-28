package io.github.mtbarr.assemblyvoting.service;

import io.github.mtbarr.assemblyvoting.data.entity.SubjectEntity;
import io.github.mtbarr.assemblyvoting.data.repository.SubjectRepository;
import io.github.mtbarr.assemblyvoting.domain.Subject;
import io.github.mtbarr.assemblyvoting.mapper.SubjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

  @Mock
  private SubjectRepository subjectRepository;

  @Mock
  private SubjectMapper subjectMapper;

  @InjectMocks
  private SubjectService subjectService;

  @Captor
  private ArgumentCaptor<SubjectEntity> subjectEntityCaptor;

  private UUID subjectId;
  private SubjectEntity subjectEntity;
  private Subject subject;
  private String title;
  private String description;

  @BeforeEach
  void setUp() {
    subjectId = UUID.randomUUID();
    title = "Test Subject";
    description = "Test Description";

    subjectEntity = new SubjectEntity();
    subjectEntity.setId(subjectId);
    subjectEntity.setTitle(title);
    subjectEntity.setDescription(description);

    subject = Subject.builder()
      .id(subjectId)
      .title(title)
      .description(description)
      .build();
  }

  @Test
  @DisplayName("Should return subject by id")
  void shouldReturnSubjectById() {
    when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subjectEntity));
    when(subjectMapper.toSubject(subjectEntity)).thenReturn(subject);

    Subject result = subjectService.getSubject(subjectId);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(subject);
    verify(subjectRepository).findById(subjectId);
    verify(subjectMapper).toSubject(subjectEntity);
  }

  @Test
  @DisplayName("Should throw RuntimeException when subject not found")
  void shouldThrowRuntimeExceptionWhenSubjectNotFound() {
    when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> subjectService.getSubject(subjectId))
      .isInstanceOf(RuntimeException.class)
      .hasMessage("Subject not found");

    verify(subjectRepository).findById(subjectId);
    verify(subjectMapper, never()).toSubject(any());
  }

  @Test
  @DisplayName("Should update subject successfully")
  void shouldUpdateSubjectSuccessfully() {
    when(subjectMapper.toEntity(subject)).thenReturn(subjectEntity);

    subjectService.updateSubject(subject);

    verify(subjectMapper).toEntity(subject);
    verify(subjectRepository).save(subjectEntity);
  }

  @Test
  @DisplayName("Should register new subject successfully")
  void shouldRegisterNewSubjectSuccessfully() {
    Subject newSubject = Subject.builder()
      .title(title)
      .description(description)
      .build();

    SubjectEntity newSubjectEntity = new SubjectEntity();
    newSubjectEntity.setTitle(title);
    newSubjectEntity.setDescription(description);

    when(subjectMapper.toEntity(any(Subject.class))).thenReturn(newSubjectEntity);
    when(subjectRepository.save(any(SubjectEntity.class))).thenReturn(subjectEntity);
    when(subjectMapper.toSubject(subjectEntity)).thenReturn(subject);

    Subject result = subjectService.registerNewSubject(title, description);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(subject);

    verify(subjectMapper).toEntity(any(Subject.class));
    verify(subjectRepository).save(any(SubjectEntity.class));
    verify(subjectMapper).toSubject(subjectEntity);
  }

  @Test
  @DisplayName("Should list all subjects with pagination")
  void shouldListAllSubjectsWithPagination() {
    Pageable pageable = PageRequest.of(0, 10);
    List<SubjectEntity> subjectEntities = List.of(subjectEntity);
    Page<SubjectEntity> subjectEntityPage = new PageImpl<>(subjectEntities, pageable, subjectEntities.size());

    when(subjectRepository.findAll(pageable)).thenReturn(subjectEntityPage);
    when(subjectMapper.toSubject(subjectEntity)).thenReturn(subject);

    Page<Subject> result = subjectService.listAllSubjects(pageable);

    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(subject);

    verify(subjectRepository).findAll(pageable);
    verify(subjectMapper).toSubject(subjectEntity);
  }

  @Test
  @DisplayName("Should list all expired and not finalized subjects")
  void shouldListAllExpiredAndNotFinalizedSubjects() {
    List<SubjectEntity> subjectEntities = List.of(subjectEntity);

    when(subjectRepository.findAllOpenAndNotFinalized(any(LocalDateTime.class))).thenReturn(subjectEntities);
    when(subjectMapper.toSubject(subjectEntity)).thenReturn(subject);

    List<Subject> result = subjectService.listAllExpiredAndNotFinalizedSubjects();

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(subject);

    verify(subjectRepository).findAllOpenAndNotFinalized(any(LocalDateTime.class));
    verify(subjectMapper).toSubject(subjectEntity);
  }

  @Test
  @DisplayName("Should list all finished subjects with pagination")
  void shouldListAllFinishedSubjectsWithPagination() {
    Pageable pageable = PageRequest.of(0, 10);
    List<SubjectEntity> subjectEntities = List.of(subjectEntity);
    Page<SubjectEntity> subjectEntityPage = new PageImpl<>(subjectEntities, pageable, subjectEntities.size());

    when(subjectRepository.findAllFinalized(pageable)).thenReturn(subjectEntityPage);
    when(subjectMapper.toSubject(subjectEntity)).thenReturn(subject);

    Page<Subject> result = subjectService.listAllFinishedSubjects(pageable);

    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(subject);

    verify(subjectRepository).findAllFinalized(pageable);
    verify(subjectMapper).toSubject(subjectEntity);
  }
}