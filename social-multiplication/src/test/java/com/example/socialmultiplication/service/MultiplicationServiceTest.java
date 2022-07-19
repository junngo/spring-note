package com.example.socialmultiplication.service;

import com.example.socialmultiplication.domain.Multiplication;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MultiplicationServiceTest {

    @MockBean
    private RandomGeneratorService randomGeneratorService;

    @Autowired
    private MultiplicationService multiplicationService;

    @Test
    public void createRandomMultiplicationTest() {
        // given
        BDDMockito.given(randomGeneratorService.generateRandomFactor())
                .willReturn(50, 30);

        // when
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        // then
        assertEquals(multiplication.getFactorA(), 50);
        assertEquals(multiplication.getFactorB(), 30);
        assertEquals(multiplication.getResult(), 1500);
    }
}