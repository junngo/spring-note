package com.example.gamification.service;

import com.example.gamification.client.MultiplicationResultAttemptClient;
import com.example.gamification.client.dto.MultiplicationResultAttempt;
import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.domain.GameStats;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.BadgeCardRepository;
import com.example.gamification.repository.ScoreCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    ScoreCardRepository scoreCardRepository;

    @Mock
    BadgeCardRepository badgeCardRepository;

    @Mock
    private MultiplicationResultAttemptClient multiplicationClient;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void processFirstCorrectAttemptTest() {
        // when
        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 10;
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(Collections.singletonList(scoreCard));
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.emptyList());

        // 기본적으로 행운의 숫자를 포함하지 않는 답안
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                "john_doe", 20, 70, 1400, true);
        given(multiplicationClient.retrieveMultiplicationResultAttemptById(anyLong()))
                .willReturn(attempt);

        // given
        GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, true);

        // then
        assertEquals(scoreCard.getScore(), gameStats.getScore());
        assertEquals(Badge.FIRST_WON, gameStats.getBadges().get(0));
    }

    @Test
    public void processCorrectAttemptForScoreBadgeTest() {
        // given
        Long userId = 1L;
        Long attemptId = 29L;
        int totalScore = 100;
        BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        // 이 리파지토리는 방금 얻은 점수 카드를 반환
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(10, userId));
        // 첫 번째 정답 배지는 이미 존재
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(firstWonBadge));

        // 기본적으로 행운의 숫자를 포함하지 않는 답안
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                "john_doe", 20, 70, 1400, true);
        given(multiplicationClient.retrieveMultiplicationResultAttemptById(anyLong()))
                .willReturn(attempt);

        // when
        GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, true);

        // assert - 점수 카드 하나와 브론즈 배지를 획득
        assertEquals(ScoreCard.DEFAULT_SCORE, gameStats.getScore());
        assertEquals(Badge.BRONZE_MULTIPLICATOR, gameStats.getBadges().get(0));
    }

    @Test
    public void processWrongAttemptTest() {
        // given
        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 10;
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);

        // when
        GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, false);

        // assert - 하나도 점수를 얻지 못함
        assertEquals(0, gameStats.getScore());
        assertEquals(0, gameStats.getBadges().size());
    }

    @Test
    public void retrieveStatsForUserTest() {
        // given
        Long userId = 1L;
        int totalScore = 1000;
        BadgeCard badgeCard = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(badgeCard));

        // when
        GameStats gameStats = gameService.retrieveStatsForUser(userId);

        // assert - 점수 카드 하나와 첫 번째 정답 배지를 획득
        assertEquals(totalScore, gameStats.getScore());
        assertEquals(Badge.SILVER_MULTIPLICATOR, gameStats.getBadges().get(0));
    }

    private List<ScoreCard> createNScoreCards(int n, Long userId) {
        return IntStream.range(0, n)
                .mapToObj(i -> new ScoreCard(userId, (long) i))
                .collect(Collectors.toList());
    }

    @Test
    public void processCorrectAttemptForLuckyNumberBadgeTest() {
        // given
        Long userId = 1L;
        Long attemptId = 29L;
        int totalScore = 10;
        BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
        given(scoreCardRepository.getTotalScoreForUser(userId))
                .willReturn(totalScore);
        // 이 리파지토리는 방금 얻은 점수 카드를 반환
        given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(createNScoreCards(1, userId));
        // 첫 번째 정답 배지는 이미 존재
        given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.singletonList(firstWonBadge));
        // 행운의 숫자가 포함된 답안
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                "john_doe", 42, 10, 420, true);
        given(multiplicationClient.retrieveMultiplicationResultAttemptById(attemptId))
                .willReturn(attempt);

        // when
        GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

        // then - 스코어 카드 하나와 행운의 숫자 배지를 획득
        assertEquals(ScoreCard.DEFAULT_SCORE, iteration.getScore());
        assertEquals(Badge.LUCKY_NUMBER, iteration.getBadges().get(0));
    }
}