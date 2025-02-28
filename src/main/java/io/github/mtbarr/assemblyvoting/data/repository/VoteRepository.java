package io.github.mtbarr.assemblyvoting.data.repository;

import io.github.mtbarr.assemblyvoting.data.entity.VoteEntity;
import io.github.mtbarr.assemblyvoting.domain.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {


  @Query("select count(v) from VoteEntity v where v.voteType = ?1 and v.subject.id = ?2")
  long countByVoteTypeAndSubjectId(VoteType voteType, UUID id);


//
//  @Query("select count(v) from VoteEntity v where v.voteType = ?1 and v.subject.id = ?2")
//  long countByVoteTypeAndSubject_Id(VoteType voteType, UUID id);


  boolean existsByAssociateIdAndSubjectId(UUID associateId, UUID subjectId);
}