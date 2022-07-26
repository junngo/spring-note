package com.example.gamification.service;


import com.example.gamification.domain.LeaderBoardRow;
import com.example.gamification.repository.ScoreCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreCardRepository scoreCardRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        return scoreCardRepository.findFirst10();
    }
}
