package com.example.socialmultiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Getter
public final class User {

    private final String alias;

    protected User() {
        alias = null;
    }
}
