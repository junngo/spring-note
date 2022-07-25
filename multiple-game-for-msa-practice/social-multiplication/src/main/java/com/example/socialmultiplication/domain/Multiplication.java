package com.example.socialmultiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
@Getter
@Entity
public final class Multiplication {

    @Column(name = "MULTIPLICATION_ID")
    @GeneratedValue
    @Id
    private Long id;

    private final int factorA;

    private final int factorB;

    Multiplication() {
        this(0, 0);
    }
}
