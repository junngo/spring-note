package com.example.socialmultiplication.controller;

import com.example.socialmultiplication.domain.MultiplicationResultAttempt;
import com.example.socialmultiplication.service.MultiplicationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/results")
@RestController
public class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
        boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt);
        MultiplicationResultAttempt attemptResult = new MultiplicationResultAttempt(
                multiplicationResultAttempt.getUser(),
                multiplicationResultAttempt.getMultiplication(),
                multiplicationResultAttempt.getResultAttempt(),
                isCorrect
        );

        return ResponseEntity.ok(attemptResult);
    }

    @NoArgsConstructor(force = true)
    @RequiredArgsConstructor
    @Getter
    static final class ResultResponse {
        private final boolean correct;
    }
}
