package com.example.socialmultiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
@Getter
public final class Multiplication {

    private final int factorA;
    private final int factorB;

    Multiplication() {
        this(0, 0);
    }
}
