package com.example.socialmultiplication.event;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventDispatcher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${multiplication.exchange}")
    private String multiplicationExchange;

    @Value("${multiplication.solved.key}")
    private String multiplicationSolvedRoutingKey;

    public void send(final MultiplicationSolvedEvent multiplicationSolvedEvent) {
        rabbitTemplate.convertAndSend(
                multiplicationExchange,
                multiplicationSolvedRoutingKey,
                multiplicationSolvedEvent
        );
    }
}
