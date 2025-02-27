package io.github.mtbarr.assemblyvoting.job;

import io.github.mtbarr.assemblyvoting.config.SessionServiceConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectSessionUpdateJob {

  private final SessionServiceConfiguration sessionServiceConfiguration;

  @Scheduled(fixedRateString = "${session-settings.update-rate}")
  public void updateVotingSessions() {
    // todo pegar todos as pautas que estão com votação em andamento e verificar se o tempo de votação expirou
    // se tiver expirado, finalizar a votação e postar uma mensagem
  }
}
