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
@ToString
@RequiredArgsConstructor
@Getter
@Entity
public final class Users {

    @Column(name = "USER_ID")
    @GeneratedValue
    @Id
    private Long id;

    private final String alias;

    protected Users() {
        alias = null;
    }
}
