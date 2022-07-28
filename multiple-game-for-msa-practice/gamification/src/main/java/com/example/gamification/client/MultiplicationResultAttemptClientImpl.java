package com.example.gamification.client;

import com.example.gamification.client.dto.MultiplicationResultAttempt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

    private final RestTemplate restTemplate;

    @Value("${multiplicationHost}")
    private String multiplicationHost;

    @Override
    public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(Long multiplicationId) {
        return restTemplate.getForObject(
                multiplicationHost +"/results/" + multiplicationId,
                MultiplicationResultAttempt.class);
    }
}
