package com.example.gamification.service;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.domain.GameStats;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.BadgeCardRepository;
import com.example.gamification.repository.ScoreCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private final ScoreCardRepository scoreCardRepository;
    private final BadgeCardRepository badgeCardRepository;

    @Override
    public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
        // 답이 맞았을 때만 점수/배지 부여
        if (correct) {
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info("사용자 ID: {}, 점수: {} 점, 답안 ID: {}",
                    userId, scoreCard.getScore(), attemptId);
            List<BadgeCard> badgeCardList = processForBadges(userId, attemptId);
            return new GameStats(
                    userId,
                    scoreCard.getScore(),
                    badgeCardList.stream()
                            .map(BadgeCard::getBadge)
                            .collect(Collectors.toList())
            );
        }

        // 답이 맞지 않다면 비어 있는 GameStats 리턴
        return GameStats.emptyStats(userId);
    }

    /**
     * 조건이 충족될 경우 새 배지를 제공하기 위해 얻은 총 점수와 점수 카드를 확인
     */
    private List<BadgeCard> processForBadges(final Long userId,
                                             final Long attemptId) {
        List<BadgeCard> badgeCards = new ArrayList<>();

        int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
        log.info("사용자 ID {} 의 새로운 점수 {}", userId, totalScore);

        List<ScoreCard> scoreCardList = scoreCardRepository
                .findByUserIdOrderByScoreTimestampDesc(userId);
        List<BadgeCard> badgeCardList = badgeCardRepository
                .findByUserIdOrderByBadgeTimestampDesc(userId);

        // 점수 기반 배지
        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId)
                .ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId)
                .ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList,
                Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId)
                .ifPresent(badgeCards::add);

        // 첫 번째 정답 배지
        if (scoreCardList.size() == 1 &&
                !containsBadge(badgeCardList, Badge.FIRST_WON)) {
            BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
            badgeCards.add(firstWonBadge);
        }

        return badgeCards;
    }

    /**
     * 배지를 얻기 위한 조건을 넘는지 체크하는 편의성 메소드
     * 또한 조건이 충족되면 사용자에게 배지를 부여
     */
    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(
            final List<BadgeCard> badgeCards,
            final Badge badge,
            final int score,
            final int scoreThreshold,
            final Long userId) {

        if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
            return Optional.of(giveBadgeToUser(badge, userId));
        }
        return Optional.empty();
    }

    /**
     * 배지 목록에 해당 배지가 포함되어 있는지 확인하는 메소드
     */
    private boolean containsBadge(final List<BadgeCard> badgeCards,
                                  final Badge badge) {
        return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
    }

    /**
     * 주어진 사용자에게 새로운 배지를 부여하는 메소드
     */
    private BadgeCard giveBadgeToUser(final Badge badge, final Long userId) {
        BadgeCard badgeCard = new BadgeCard(userId, badge);
        badgeCardRepository.save(badgeCard);
        log.info("사용자 ID {} 새로운 배지 획득: {}", userId, badge);
        return badgeCard;
    }

    @Override
    public GameStats retrieveStatsForUser(Long userId) {
        int score = scoreCardRepository.getTotalScoreForUser(userId);
        List<BadgeCard> badgeCards = badgeCardRepository
                .findByUserIdOrderByBadgeTimestampDesc(userId);
        return new GameStats(userId, score, badgeCards.stream()
                .map(BadgeCard::getBadge).collect(Collectors.toList()));
    }
}
