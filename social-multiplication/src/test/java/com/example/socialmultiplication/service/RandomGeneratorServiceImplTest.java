package com.example.socialmultiplication.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RandomGeneratorServiceImplTest {

    @InjectMocks
    private RandomGeneratorServiceImpl randomGeneratorServiceImpl;

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits() {
        // given
        // when
        // Generated random number
        List<Integer> randomFactors = IntStream.range(0, 1000)
                .map(i -> randomGeneratorServiceImpl.generateRandomFactor())
                .boxed()
                .collect(Collectors.toList());

        // then
        // Check number between 11 and 99 number
        assertThat(randomFactors)
                .containsOnlyElementsOf(
                        IntStream.range(11, 100)
                        .boxed()
                        .collect(Collectors.toList())
                );
    }
}