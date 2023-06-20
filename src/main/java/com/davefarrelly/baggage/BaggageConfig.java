package com.davefarrelly.baggage;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaggageConfig {
    @Bean
    BaggageField traceIdField() {
        return BaggageField.create("TRACE_ID");
    }

    @Bean
    public CurrentTraceContext.ScopeDecorator mdcScopeDecorator(BaggageField traceIdField) {
        return MDCScopeDecorator.newBuilder()
                .clear()
                .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(traceIdField).flushOnUpdate().build())
                .build();
    }
}
