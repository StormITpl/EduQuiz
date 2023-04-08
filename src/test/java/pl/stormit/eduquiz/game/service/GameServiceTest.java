package pl.stormit.eduquiz.game.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.dto.GameMapper;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class GameServiceTest {

    private static final UUID ID_1 = UUID.fromString("5d1b4c2c-9f1c-11ed-a8fc-0242ac120002");
    private static final UUID ID_2 = UUID.fromString("5d1b4c2c-9f1c-11ed-a8fc-0242ac120003");

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private QuizRepository quizRepository;

    @Autowired
    private GameService gameService;

    @MockBean
    private GameMapper gameMapper;

    @Test
    void shouldCreateGameCorrectly() {

        //given
        Quiz quiz = new Quiz();
        quiz.setName("Quiz");
        quiz.setId(ID_1);
        QuizDto quizDto = new QuizDto(ID_1, "Quiz", null, null, List.of(), List.of());
        Game game = new Game(ID_2, null, quiz);
        GameDto gameDto = new GameDto(ID_2, null);

        //when
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameMapper.mapGameEntityToGameDto(game)).thenReturn(gameDto);
        when(quizRepository.findById(any())).thenReturn(Optional.of(quiz));
        GameDto returnGame = gameService.createGame(quizDto);

        //then
        assertEquals(game.getId(), returnGame.id());
        assertEquals(game.getUserAnswers(), returnGame.userAnswers());
    }

    @Test
    void shouldReturnGame() {

        //given
        Quiz quiz = new Quiz();
        quiz.setName("Quiz");
        quiz.setId(ID_1);
        QuizDto quizDto = new QuizDto(ID_1, "Quiz", null, null, List.of(), List.of());
        Game game = new Game(ID_2, null, quiz);
        GameDto gameDto = new GameDto(ID_2, null);

        //when
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameMapper.mapGameEntityToGameDto(game)).thenReturn(gameDto);
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        GameDto returnedGameDto = gameService.getGame(ID_2);

        //then
        assertEquals(game.getId(), returnedGameDto.id());
        //assertEquals(returnedGameDto., "Quiz");
        assertNull(returnedGameDto.userAnswers());
    }

    @Test
    void shouldDeleteGame() {

        //given
        Quiz quiz = new Quiz();
        quiz.setName("Quiz");
        quiz.setId(ID_1);
        Game game = new Game(ID_1, null, quiz);

        //when
        Mockito.doNothing().when(gameRepository).deleteById(game.getId());

        //then
        gameService.deleteGame(game.getId());
        Mockito.verify(gameRepository, Mockito.times(1)).deleteById(game.getId());
        Mockito.verifyNoMoreInteractions(gameRepository);
    }
}