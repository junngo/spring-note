package com.example.socialmultiplication.controller;

import com.example.socialmultiplication.domain.Multiplication;
import com.example.socialmultiplication.domain.MultiplicationResultAttempt;
import com.example.socialmultiplication.domain.Users;
import com.example.socialmultiplication.service.MultiplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(MultiplicationResultAttemptController.class)
class MultiplicationResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<MultiplicationResultAttempt> jsonResult;
    private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnCorrect() throws Exception {
        genericParameterizedTest(true);
    }

    @Test
    public void postResultReturnNotCorrect() throws Exception {
        genericParameterizedTest(false);
    }

    void genericParameterizedTest(final boolean correct) throws Exception {
        // given
        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class)))
                .willReturn(correct);

        Users user = new Users("jun");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, correct);

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResult.write(attempt).getJson()))
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(
                jsonResult.write(
                        new MultiplicationResultAttempt(
                                attempt.getUser(),
                                attempt.getMultiplication(),
                                attempt.getResultAttempt(),
                                correct
                        )
                ).getJson()
                , response.getContentAsString()
        );
    }

    @Test
    public void getUserStats() throws Exception {
        // given
        Users user = new Users("jun");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt =
                new MultiplicationResultAttempt(user, multiplication, 3500, true);
        List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
        given(multiplicationService.getStatsForUser("jun"))
                .willReturn(recentAttempts);

        // when
        MockHttpServletResponse response = mvc.perform(
                    get("/results")
                            .param("alias", "jun")
                )
                .andReturn()
                .getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(
                jsonResultAttemptList.write(recentAttempts).getJson(),
                response.getContentAsString()
        );
    }
}