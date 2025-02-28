package io.github.mtbarr.assemblyvoting.service;

import io.github.mtbarr.assemblyvoting.config.SessionServiceConfiguration;
import io.github.mtbarr.assemblyvoting.data.entity.AssociateEntity;
import io.github.mtbarr.assemblyvoting.data.repository.AssociateRepository;
import io.github.mtbarr.assemblyvoting.domain.Associate;
import io.github.mtbarr.assemblyvoting.exception.InvalidCpfException;
import io.github.mtbarr.assemblyvoting.exception.ResourceNotFoundException;
import io.github.mtbarr.assemblyvoting.integration.CpfIntegration;
import io.github.mtbarr.assemblyvoting.mapper.AssociateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociateServiceTest {

  @Mock
  private AssociateRepository associateRepository;

  @Mock
  private AssociateMapper associateMapper;

  @Mock
  private SessionServiceConfiguration sessionServiceConfiguration;

  @Mock
  private CpfIntegration cpfIntegration;

  @InjectMocks
  private AssociateService associateService;

  private UUID associateId;
  private String validCpf;
  private String formattedCpf;
  private AssociateEntity associateEntity;
  private Associate associate;

  @BeforeEach
  void setUp() {
    associateId = UUID.randomUUID();
    validCpf = "123.456.789-09";
    formattedCpf = "12345678909";
    associateEntity = new AssociateEntity();
    associateEntity.setId(associateId);
    associateEntity.setCpf(formattedCpf);

    associate = Associate.builder()
      .id(associateId)
      .cpf(formattedCpf)
      .build();
  }

  @Test
  @DisplayName("Should return all associates with pagination")
  void shouldReturnAllAssociatesWithPagination() {
    Pageable pageable = PageRequest.of(0, 10);
    List<AssociateEntity> associateEntities = List.of(associateEntity);
    Page<AssociateEntity> associateEntityPage = new PageImpl<>(associateEntities, pageable, associateEntities.size());

    when(associateRepository.findAll(pageable)).thenReturn(associateEntityPage);
    when(associateMapper.toAssociate(associateEntity)).thenReturn(associate);

    Page<Associate> result = associateService.getAllAssociates(pageable);

    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0)).isEqualTo(associate);
    verify(associateRepository).findAll(pageable);
    verify(associateMapper).toAssociate(associateEntity);
  }

  @Test
  @DisplayName("Should return associate by id")
  void shouldReturnAssociateById() {
    when(associateRepository.findById(associateId)).thenReturn(Optional.of(associateEntity));
    when(associateMapper.toAssociate(associateEntity)).thenReturn(associate);

    Associate result = associateService.getAssociate(associateId);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(associate);
    verify(associateRepository).findById(associateId);
    verify(associateMapper).toAssociate(associateEntity);
  }

  @Test
  @DisplayName("Should throw ResourceNotFoundException when associate not found")
  void shouldThrowResourceNotFoundExceptionWhenAssociateNotFound() {
    when(associateRepository.findById(associateId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> associateService.getAssociate(associateId))
      .isInstanceOf(ResourceNotFoundException.class)
      .hasMessageContaining("Associate not found with id: " + associateId);

    verify(associateRepository).findById(associateId);
  }

  @Test
  @DisplayName("Should register new associate successfully")
  void shouldRegisterNewAssociateSuccessfully() {
    when(sessionServiceConfiguration.isCpfValidationEnabled()).thenReturn(true);
    when(cpfIntegration.isValidCpf(formattedCpf)).thenReturn(true);
    when(associateRepository.existsByCpf(formattedCpf)).thenReturn(false);
    when(associateMapper.toEntity(any(Associate.class))).thenReturn(associateEntity);
    when(associateRepository.save(associateEntity)).thenReturn(associateEntity);
    when(associateMapper.toAssociate(associateEntity)).thenReturn(associate);

    Associate result = associateService.registerNewAssociate(validCpf);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(associate);
    verify(sessionServiceConfiguration).isCpfValidationEnabled();
    verify(cpfIntegration).isValidCpf(formattedCpf);
    verify(associateRepository).existsByCpf(formattedCpf);
    verify(associateMapper).toEntity(any(Associate.class));
    verify(associateRepository).save(associateEntity);
    verify(associateMapper).toAssociate(associateEntity);
  }

  @Test
  @DisplayName("Should register new associate successfully when CPF validation is disabled")
  void shouldRegisterNewAssociateSuccessfullyWhenCpfValidationIsDisabled() {
    when(sessionServiceConfiguration.isCpfValidationEnabled()).thenReturn(false);
    when(associateRepository.existsByCpf(formattedCpf)).thenReturn(false);
    when(associateMapper.toEntity(any(Associate.class))).thenReturn(associateEntity);
    when(associateRepository.save(associateEntity)).thenReturn(associateEntity);
    when(associateMapper.toAssociate(associateEntity)).thenReturn(associate);

    Associate result = associateService.registerNewAssociate(validCpf);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(associate);
    verify(sessionServiceConfiguration).isCpfValidationEnabled();
    verify(cpfIntegration, never()).isValidCpf(any());
    verify(associateRepository).existsByCpf(formattedCpf);
    verify(associateMapper).toEntity(any(Associate.class));
    verify(associateRepository).save(associateEntity);
    verify(associateMapper).toAssociate(associateEntity);
  }

  @Test
  @DisplayName("Should throw InvalidCpfException when CPF syntax is invalid")
  void shouldThrowInvalidCpfExceptionWhenCpfSyntaxIsInvalid() {
    String invalidCpf = "invalid-cpf";
    when(sessionServiceConfiguration.isCpfValidationEnabled()).thenReturn(true);

    assertThatThrownBy(() -> associateService.registerNewAssociate(invalidCpf))
      .isInstanceOf(InvalidCpfException.class)
      .hasMessageContaining("CPF Syntax is Invalid");

    verify(sessionServiceConfiguration).isCpfValidationEnabled();
    verify(cpfIntegration, never()).isValidCpf(any());
    verify(associateRepository, never()).existsByCpf(any());
  }

  @Test
  @DisplayName("Should throw InvalidCpfException when CPF is invalid according to integration")
  void shouldThrowInvalidCpfExceptionWhenCpfIsInvalidAccordingToIntegration() {
    when(sessionServiceConfiguration.isCpfValidationEnabled()).thenReturn(true);
    when(cpfIntegration.isValidCpf(formattedCpf)).thenReturn(false);

    assertThatThrownBy(() -> associateService.registerNewAssociate(validCpf))
      .isInstanceOf(InvalidCpfException.class)
      .hasMessageContaining("Associate has a invalid CPF");

    verify(sessionServiceConfiguration).isCpfValidationEnabled();
    verify(cpfIntegration).isValidCpf(formattedCpf);
    verify(associateRepository, never()).existsByCpf(any());
  }

  @Test
  @DisplayName("Should throw ResourceNotFoundException when associate with CPF already exists")
  void shouldThrowResourceNotFoundExceptionWhenAssociateWithCpfAlreadyExists() {
    when(sessionServiceConfiguration.isCpfValidationEnabled()).thenReturn(true);
    when(cpfIntegration.isValidCpf(formattedCpf)).thenReturn(true);
    when(associateRepository.existsByCpf(formattedCpf)).thenReturn(true);

    assertThatThrownBy(() -> associateService.registerNewAssociate(validCpf))
      .isInstanceOf(ResourceNotFoundException.class)
      .hasMessageContaining("Associate already registered with CPF");

    verify(sessionServiceConfiguration).isCpfValidationEnabled();
    verify(cpfIntegration).isValidCpf(formattedCpf);
    verify(associateRepository).existsByCpf(formattedCpf);
    verify(associateRepository, never()).save(any());
  }
}