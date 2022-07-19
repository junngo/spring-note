package com.example.socialmultiplication.service;

import com.example.socialmultiplication.domain.Multiplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MultiplicationServiceImplTest {

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
        assertEquals(multiplication.getResult(), 1500);
    }
}