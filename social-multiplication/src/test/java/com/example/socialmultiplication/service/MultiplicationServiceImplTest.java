package com.example.socialmultiplication.service;

import com.example.socialmultiplication.domain.Multiplication;
import com.example.socialmultiplication.domain.MultiplicationResultAttempt;
import com.example.socialmultiplication.domain.Users;
import com.example.socialmultiplication.repository.MultiplicationResultAttemptRepository;
import com.example.socialmultiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MultiplicationServiceImplTest {

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @InjectMocks
    private MultiplicationServiceImpl multiplicationService;

    @Test
    public void createRandomMultiplicationTest() {
        // given
        given(randomGeneratorService.generateRandomFactor())
                .willReturn(50, 30);

        // when
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        // then
        assertEquals(multiplication.getFactorA(), 50);
        assertEquals(multiplication.getFactorB(), 30);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        Users user = new Users("jun");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
        given(userRepository.findByAlias("jun"))
                .willReturn(Optional.empty());

        // when
        boolean attemptResult = multiplicationService.checkAttempt(attempt);

        // then
        assertTrue(attemptResult);
        verify(attemptRepository).save(verifiedAttempt);
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        Users user = new Users("jun");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 500, false);
        given(userRepository.findByAlias("jun"))
                .willReturn(Optional.empty());

        // when
        boolean attemptResult = multiplicationService.checkAttempt(attempt);

        // then
        assertFalse(attemptResult);
        verify(attemptRepository).save(attempt);
    }

    @Test
    public void retrieveStatsTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        Users user = new Users("jun");
        MultiplicationResultAttempt attempt1 =
                new MultiplicationResultAttempt(user, multiplication, 3010, false);
        MultiplicationResultAttempt attempt2 =
                new MultiplicationResultAttempt(user, multiplication, 3051, false);
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);

        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("jun"))
                .willReturn(latestAttempts);

        // when
        List<MultiplicationResultAttempt> latestAttemptResult =
                multiplicationService.getStatsForUser("jun");

        // then
        assertEquals(latestAttempts, latestAttemptResult);
    }
}