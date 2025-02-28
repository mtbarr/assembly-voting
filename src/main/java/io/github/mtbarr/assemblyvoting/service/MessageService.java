package io.github.mtbarr.assemblyvoting.service;

import io.github.mtbarr.assemblyvoting.messaging.VotingSessionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final KafkaTemplate<String, VotingSessionResult> kafkaTemplate;

  public void publishSessionResult(VotingSessionResult result) {
    kafkaTemplate.send("voting-session-result", result);
  }
}
