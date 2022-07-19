package com.example.socialmultiplication.service;

public interface RandomGeneratorService {

    /**
     * Separate Generator for test code
     * If Number is created in MultiplicationService, Testing'll become hard.
     *
     * @return random number: 11 ~ 99
     */
    int generateRandomFactor();
}
