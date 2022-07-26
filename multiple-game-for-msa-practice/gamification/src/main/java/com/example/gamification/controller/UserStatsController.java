package com.example.gamification.controller;

import com.example.gamification.domain.GameStats;
import com.example.gamification.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gamification 사용자 통계 서비스의 REST API
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/stats")
public class UserStatsController {

    private final GameService gameService;

    @GetMapping
    public GameStats getStatsForUser(@RequestParam("userId") final Long userId) {
        return gameService.retrieveStatsForUser(userId);
    }
}
