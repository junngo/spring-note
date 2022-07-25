package com.example.socialmultiplication.domain;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Getter
@Entity
public final class MultiplicationResultAttempt {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private final Users user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MULTIPLICATION_ID")
    private final Multiplication multiplication;

    private final int resultAttempt;

    private final boolean correct;

    MultiplicationResultAttempt() {
        user = null;
        multiplication = null;
        resultAttempt = -1;
        correct = false;
    }
}
