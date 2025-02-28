package io.github.mtbarr.assemblyvoting.data.repository;

import io.github.mtbarr.assemblyvoting.data.entity.AssociateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssociateRepository extends JpaRepository<AssociateEntity, UUID> {

  boolean existsByCpf(String cpf);

}
