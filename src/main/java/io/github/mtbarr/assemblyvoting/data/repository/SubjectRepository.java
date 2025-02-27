package io.github.mtbarr.assemblyvoting.data.repository;

import io.github.mtbarr.assemblyvoting.data.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<SubjectEntity, UUID> {


  @NativeQuery(
    """
        SELECT COUNT(1) > 0
        FROM SubjectEntity
        WHERE id = :id
        AND votingEndTime >= CURRENT_TIMESTAMP
        AND finalized = false
      """
  )
  boolean isRunningSessionById(UUID id);

  @NativeQuery(
    """
        SELECT COUNT(1) > 0
        FROM SubjectEntity
        WHERE id = :id
        AND votingEndTime < CURRENT_TIMESTAMP
      """
  )
  boolean isExpiredById(UUID id);

  @NativeQuery(
    """
        SELECT finalized
        FROM SubjectEntity
        WHERE id = :id
      """
  )
  boolean isFinalizedById(UUID id);

  @NativeQuery(
    """
        UPDATE SubjectEntity
        SET finalized = true
        WHERE id = :id
      """
  )
  void setSessionFinalizedById(UUID id, boolean finalized);


}
