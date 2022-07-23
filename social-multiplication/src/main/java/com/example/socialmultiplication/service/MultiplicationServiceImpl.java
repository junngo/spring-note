package com.example.socialmultiplication.service;

import com.example.socialmultiplication.domain.Multiplication;
import com.example.socialmultiplication.domain.MultiplicationResultAttempt;
import com.example.socialmultiplication.domain.Users;
import com.example.socialmultiplication.repository.MultiplicationResultAttemptRepository;
import com.example.socialmultiplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;
    private final MultiplicationResultAttemptRepository attemptRepository;
    private final UserRepository userRepository;

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Override
    public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
        Optional<Users> user = userRepository.findByAlias(resultAttempt.getUser().getAlias());

        boolean correct = resultAttempt.getResultAttempt() ==
                (resultAttempt.getMultiplication().getFactorA() *
                        resultAttempt.getMultiplication().getFactorB()
                );

        MultiplicationResultAttempt checkedAttempt =
                new MultiplicationResultAttempt(
                        user.orElse(resultAttempt.getUser()),
                        resultAttempt.getMultiplication(),
                        resultAttempt.getResultAttempt(),
                        correct
                );
        attemptRepository.save(checkedAttempt);

        return correct;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }
}
