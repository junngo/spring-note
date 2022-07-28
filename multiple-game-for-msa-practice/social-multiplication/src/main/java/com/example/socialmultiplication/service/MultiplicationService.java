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

    /**
     * 해당 사용자의 통계 정보를 조회한다.
     *
     * @param userAlias는 해당 사용자의 닉네임
     * @return 해당 사용자가 전에 제출한 답안 객체 {@link MultiplicationResultAttempt}의 리스트
     */
    List<MultiplicationResultAttempt> getStatsForUser(String userAlias);

    /**
     * ID에 해당하는 답안 조회
     *
     * @param resultId 답안의 식별자
     * @return ID에 해당하는 {@link MultiplicationResultAttempt} 객체, 없으면 null
     */
    MultiplicationResultAttempt getResultById(final Long resultId);
}
