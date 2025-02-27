package io.github.mtbarr.assemblyvoting.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Associate {

  private UUID id;
  private String cpf;
}
