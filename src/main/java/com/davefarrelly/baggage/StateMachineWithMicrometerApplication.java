package com.davefarrelly.baggage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class StateMachineWithMicrometerApplication {

  public static void main(String[] args) {
    SpringApplication.run(StateMachineWithMicrometerApplication.class, args);
  }

}
