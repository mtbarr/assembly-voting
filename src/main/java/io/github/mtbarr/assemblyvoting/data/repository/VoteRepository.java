package io.github.mtbarr.assemblyvoting.data.repository;

import io.github.mtbarr.assemblyvoting.data.entity.VoteEntity;
import io.github.mtbarr.assemblyvoting.data.projection.SessionResultProjection;
import io.github.mtbarr.assemblyvoting.data.projection.VoteCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {

  @NativeQuery("""
    SELECT
      COALESCE(SUM(CASE WHEN v.vote_type = 'YES' THEN 1 ELSE 0 END), 0) AS yesVotes,
      COALESCE(SUM(CASE WHEN v.vote_type = 'NO' THEN 1 ELSE 0 END), 0) AS noVotes
    FROM vote v
    WHERE v.subject_id = :subjectId
    """
  )
  VoteCountProjection countVotesBySubjectId(@Param("subjectId") UUID subjectId);


  @NativeQuery("""
    SELECT
      s.id AS subjectId,
      s.title AS subjectTitle,
      s.description AS subjectDescription,
      COALESCE(SUM(CASE WHEN v.vote_type = 'YES' THEN 1 ELSE 0 END), 0) AS yesVotes,
      COALESCE(SUM(CASE WHEN v.vote_type = 'NO' THEN 1 ELSE 0 END), 0) AS noVotes,
    FROM vote v
    JOIN subject s ON v.subject_id = s.id
    GROUP BY s.id
    """
  )
  Page<SessionResultProjection> getAllFinalizedSessionResults(Pageable pageable);

  boolean existsByAssociateIdAndSubjectId(UUID associateId, UUID subjectId);
}