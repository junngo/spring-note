package com.example.socialmultiplication.service;

import com.example.socialmultiplication.domain.Multiplication;

public interface MultiplicationService {

    /**
     * Created two random numbers
     * Number range: 11 ~ 99
     *
     * @return
     */
    Multiplication createRandomMultiplication();
}
