package pl.stormit.eduquiz.result.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.dto.GameIdDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.domain.repository.ResultRepository;
import pl.stormit.eduquiz.result.dto.ResultDto;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class ResultServiceTest {

    private static final UUID FIRST_ID = UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82501");
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ResultService resultService;

    @Test
    void shouldReturnResultById() {
        //given
        Result result = new Result();
        Result savedResult = resultRepository.save(result);

        //when
        ResultDto resultDto = resultService.getResult(savedResult.getId());

        //then
        assertFalse(resultDto.id().toString().isEmpty());
    }

    @Test
    void shouldCreateResultUsingGameId() {
        //given
        Game game = new Game();
        Question exemplaryQuestion = new Question();
        Answer exemplaryAnswer = new Answer();
        exemplaryAnswer.setId(FIRST_ID);
        exemplaryAnswer.setCorrect(true);
        exemplaryQuestion.setAnswers(List.of(exemplaryAnswer));
        Quiz exemplaryQuiz = new Quiz(FIRST_ID, "Royal", null,
                null, List.of(exemplaryQuestion), List.of(game));
        game.setQuiz(exemplaryQuiz);
        game.setUserAnswers(List.of(UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82502")));
        Game savedGame = gameRepository.save(game);
        GameIdDto gameIdDto = new GameIdDto(savedGame.getId());

        //when
        ResultDto resultDto = resultService.createResult(gameIdDto);

        //then
        assertFalse(resultDto.id().toString().isEmpty());
        assertEquals(resultDto.game(), savedGame);
    }

    @Test
    void shouldDeleteResult() {
        //given
        Result result = new Result();
        Result savedResult = resultRepository.save(result);
        UUID id = savedResult.getId();

        //when
        resultService.deleteResult(savedResult.getId());

        //then
        assertTrue(resultRepository.findById(savedResult.getId()).isEmpty());
        assertThrows(EntityNotFoundException.class, () -> resultService.getResult(id));
    }
}
