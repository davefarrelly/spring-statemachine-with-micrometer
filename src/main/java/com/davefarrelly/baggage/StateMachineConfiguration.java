package com.davefarrelly.baggage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import reactor.core.publisher.Mono;

@Configuration
@EnableStateMachineFactory
@Slf4j
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<String, String> {

    private static final String START = "START";
    private static final String FIRST = "FIRST";
    private static final String SECOND = "SECOND";
    private static final String THIRD = "THIRD";
    private static final String END = "END";

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states.withStates()
                .initial(START, startAction())
                .state(FIRST, firstAction())
                .state(SECOND, secondAction())
                .state(THIRD, thirdAction())
                .end(END);
    }

    public Action<String, String> startAction() {
        return context -> {
            log.info("Action called!");
            Message<String> stateEvent = MessageBuilder.withPayload(FIRST).build();
            context.getStateMachine().sendEvent(Mono.just(stateEvent)).subscribe();
        };
    }
    public Action<String, String> firstAction() {
        return context -> {
            log.info("Action called!");
            Message<String> stateEvent = MessageBuilder.withPayload(SECOND).build();
            context.getStateMachine().sendEvent(Mono.just(stateEvent)).subscribe();
        };
    }

    public Action<String, String> secondAction() {
        return context -> {
            log.info("Action called!");
            Message<String> stateEvent = MessageBuilder.withPayload(THIRD).build();
            context.getStateMachine().sendEvent(Mono.just(stateEvent)).subscribe();
        };
    }

    public Action<String, String> thirdAction() {
        return context -> {
            log.info("Action called!");
            Message<String> stateEvent = MessageBuilder.withPayload(END).build();
            context.getStateMachine().sendEvent(Mono.just(stateEvent)).subscribe();
        };
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.withExternal()
                .source(START)
                .target(FIRST)
                .and().withExternal()
                .source(FIRST)
                .target(SECOND)
                .event(SECOND)
                .and().withExternal()
                .source(SECOND)
                .target(THIRD)
                .event(THIRD)
                .and().withExternal()
                .source(THIRD)
                .target(END)
                .event(END);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        StateMachineListenerAdapter<String, String> adapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                log.info("State changed to: " + to.getId());
            }
        };

        config.withConfiguration().listener(adapter);
    }
}
