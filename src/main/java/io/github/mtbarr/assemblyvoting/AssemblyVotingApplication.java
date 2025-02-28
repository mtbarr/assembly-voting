package io.github.mtbarr.assemblyvoting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class AssemblyVotingApplication {

  public static void main(String[] args) {
    SpringApplication.run(AssemblyVotingApplication.class, args);
  }
}
