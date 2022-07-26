package com.example.gamification.domain;

/**
 * 사용자가 획득할 수 있는 여러 종류의 배지를 열거
 */
public enum Badge {

    // through SCORE
    BRONZE_MULTIPLICATOR,
    SILVER_MULTIPLICATOR,
    GOLD_MULTIPLICATOR,

    // through CONDITION
    FIRST_ATTEMPT,
    FIRST_WON,
    LUCKY_NUMBER
}
