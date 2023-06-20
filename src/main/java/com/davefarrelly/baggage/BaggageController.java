package com.davefarrelly.baggage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/statemachine/")
public class BaggageController {

  private final StateMachineFactory<String, String> stateMachineFactory;

  @PostMapping("start")
  public Mono<Void> getCommitStatus() {

    log.info("Starting state machine");

    StateMachine<String, String> sm = stateMachineFactory.getStateMachine();

    return sm.startReactively();
  }

}
