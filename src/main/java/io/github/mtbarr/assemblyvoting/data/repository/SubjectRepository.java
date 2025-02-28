package io.github.mtbarr.assemblyvoting.data.repository;

import io.github.mtbarr.assemblyvoting.data.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<SubjectEntity, UUID> {

  @Query("select s from SubjectEntity s where s.finalized = false and s.votingEndTime < ?1")
  List<SubjectEntity> findAllOpenAndNotFinalized(LocalDateTime votingEndTime);

  @Query("select s from SubjectEntity s where s.finalized = true")
  List<SubjectEntity> findAllFinalized();
}
