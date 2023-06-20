package com.davefarrelly.baggage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachineFactory
@Slf4j
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<String, String> {

    private static final String START = "START";
    private static final String MIDDLE = "MIDDLE";
    private static final String END = "END";

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states.withStates()
                .initial(START)
                .state(MIDDLE)
                .end(END);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions.withExternal()
                .source(START)
                .target(MIDDLE)
                .and().withExternal()
                .source(MIDDLE)
                .target(END);
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
