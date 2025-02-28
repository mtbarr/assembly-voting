package io.github.mtbarr.assemblyvoting.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
  @Bean
  public NewTopic votingSessionResultTopic() {
    return new NewTopic("voting-session-result", 1, (short) 1);
  }
}