package com.example.socialmultiplication.domain;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Multiplication {

    private int factorA;
    private int factorB;
    private int result;

    public Multiplication(int factorA, int factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
        this.result = factorA * factorB;
    }
}
