package com.example.socialmultiplication.service;

import com.example.socialmultiplication.domain.Multiplication;
import com.example.socialmultiplication.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {

    /**
     * Created two random numbers
     * Number range: 11 ~ 99
     *
     * @return
     */
    Multiplication createRandomMultiplication();

    /**
     * Check the answer
     *
     * @param resultAttempt
     * @return If the answer is right, Return the true; otherwise return false
     */
    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias);
}
