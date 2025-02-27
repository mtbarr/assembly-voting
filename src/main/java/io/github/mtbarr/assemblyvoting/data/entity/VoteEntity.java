package io.github.mtbarr.assemblyvoting.data.entity;

import io.github.mtbarr.assemblyvoting.domain.VoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vote")
public class VoteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "subject_id", nullable = false)
  private SubjectEntity subject;

  @ManyToOne
  @JoinColumn(name = "associate_id", nullable = false)
  private AssociateEntity associate;

  @Enumerated
  @Column(nullable = false)
  private VoteType voteType;
}
