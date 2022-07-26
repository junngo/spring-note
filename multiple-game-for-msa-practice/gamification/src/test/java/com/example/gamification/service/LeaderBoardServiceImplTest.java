package com.example.gamification.service;

import com.example.gamification.domain.LeaderBoardRow;
import com.example.gamification.repository.ScoreCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LeaderBoardServiceImplTest {

    @Mock
    private ScoreCardRepository scoreCardRepository;

    @InjectMocks
    private LeaderBoardServiceImpl leaderBoardService;

    @Test
    public void retrieveLeaderBoardTest() {
        // given
        Long userId = 1L;
        LeaderBoardRow leaderRow1 = new LeaderBoardRow(userId, 300L);
        List<LeaderBoardRow> expectedLeaderBoard = Collections.singletonList(leaderRow1);
        given(scoreCardRepository.findFirst10())
                .willReturn(expectedLeaderBoard);

        // when
        List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();

        // then
        assertEquals(leaderBoard, expectedLeaderBoard);
    }
}