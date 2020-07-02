package com.rosreestr.app;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoAppApplication.class, args);
  }

  @Bean
  public ApplicationRunner init() {
    return args -> {
      // for test purpose
    };
  }
}
