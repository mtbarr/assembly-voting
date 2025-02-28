package io.github.mtbarr.assemblyvoting.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SessionServiceConfiguration {

  @Value("${session-settings.validate-cpf}")
  private boolean cpfValidationEnabled;

  @Value("${session-settings.endpoint-url}")
  private String endpointUrl;

  @Value("${session-settings.post-result-on-end}")
  private boolean messagePostOnSessionEndEnabled;
}
