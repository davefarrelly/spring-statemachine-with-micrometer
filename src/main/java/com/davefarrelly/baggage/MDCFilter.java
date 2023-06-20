package com.davefarrelly.baggage;

import brave.baggage.BaggageField;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class MDCFilter implements WebFilter {

    private final BaggageField traceIdField;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        UUID uuid = UUID.randomUUID();

        traceIdField.updateValue(uuid.toString());

        return chain.filter(exchange);
    }
}
