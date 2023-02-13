package pl.stormit.eduquiz.gameTests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class GameControllerTest {


    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Test
    void shouldGetQuestions() throws Exception {
        List<Question> questionsList = gameService.findAllQuizQuestions(UUID.randomUUID());
        Assertions.assertEquals(List.of(), questionsList);
    }
}
