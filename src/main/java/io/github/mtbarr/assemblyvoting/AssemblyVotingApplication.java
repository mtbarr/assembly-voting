package io.github.mtbarr.assemblyvoting;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Locale;
import java.util.TimeZone;

@EnableFeignClients
@EnableCaching
@SpringBootApplication
public class AssemblyVotingApplication {

  public static void main(String[] args) {
    SpringApplication.run(AssemblyVotingApplication.class, args);
  }
}
