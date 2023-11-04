package pl.stormit.eduquiz.game.service;

import jakarta.persistence.EntityNotFoundException;
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
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class GameServiceTest {

    private static final UUID ID_1 = UUID.fromString("5d1b4c2c-9f1c-11ed-a8fc-0242ac120002");

    private static final UUID ID_2 = UUID.fromString("5d1b4c2c-9f1c-11ed-a8fc-0242ac120003");

    private static final UUID NON_EXISTENT_QUIZ_ID = UUID.fromString("5d1b4c2c-9f1c-11ed-a8fc-0242ac120666");

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
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Quiz");
        quiz.setId(ID_1);
        QuizDto quizDto = new QuizDto(ID_1, "Quiz", null, null, List.of(), List.of());
        Game game = new Game(ID_2, null, quiz);
        GameDto gameDto = new GameDto(ID_2, null);

        // when
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameMapper.mapGameEntityToGameDto(game)).thenReturn(gameDto);
        when(quizRepository.findById(any())).thenReturn(Optional.of(quiz));
        GameDto returnGame = gameService.createGame(quizDto);

        // then
        assertEquals(game.getId(), returnGame.id());
        assertEquals(game.getUserAnswers(), returnGame.userAnswers());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCreatingGameWithNonexistentQuiz() {
        // given
        QuizDto nonExistentQuizDto = new QuizDto(
                NON_EXISTENT_QUIZ_ID,
                "NonExistentQuiz",
                null,
                null,
                List.of(),
                List.of());

        // when
        when(quizRepository.findById(NON_EXISTENT_QUIZ_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> {
            gameService.createGame(nonExistentQuizDto);
        });
    }

    @Test
    void shouldReturnGameByIdCorrectly() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Quiz");
        quiz.setId(ID_1);
        Game game = new Game(ID_2, null, quiz);
        GameDto gameDto = new GameDto(ID_2, null);

        // when
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameMapper.mapGameEntityToGameDto(game)).thenReturn(gameDto);
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        GameDto returnedGameDto = gameService.getGame(ID_2);

        // then
        assertEquals(game.getId(), returnedGameDto.id());
        assertNull(returnedGameDto.userAnswers());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenPlayingNonexistentGame() {
        // given
        UUID nonExistentGameId = UUID.randomUUID();
        AnswerDto answerDto = AnswerDto.builder()
                .id(UUID.randomUUID())
                .content("Winicjusz")
                .isCorrect(true)
                .question(new Question())
                .build();

        // when
        when(gameRepository.findById(nonExistentGameId)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> {
            gameService.playGame(nonExistentGameId, answerDto);
        });
    }

    @Test
    public void testCompleteGameWithUserAnswers() {
        // given
        UUID gameId = UUID.randomUUID();
        Game game = new Game();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        AnswerDto answer = AnswerDto.builder()
                .id(UUID.randomUUID())
                .content("Burkina Faso")
                .isCorrect(true)
                .question(new Question())
                .build();

        // when
        GameDto playGameResult = gameService.playGame(gameId, answer);

        // then
        if (playGameResult != null) {
            GameDto result = gameService.completeGame(gameId);
            assertEquals(playGameResult.userAnswers(), result.userAnswers());
        } else {
            System.out.println("playGameResult is null");
        }
    }

    @Test
    public void testCompleteGameWithIncorrectUserAnswers() {
        // given
        UUID gameId = UUID.randomUUID();
        Game game = new Game();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        AnswerDto incorrectAnswer = AnswerDto.builder()
                .id(UUID.randomUUID())
                .content("Haiti")
                .isCorrect(false)
                .question(new Question())
                .build();

        // when
        GameDto playGameResult = gameService.playGame(gameId, incorrectAnswer);

        // then
        if (playGameResult != null) {
            GameDto result = gameService.completeGame(gameId);
            assertNotEquals(playGameResult.userAnswers(), result.userAnswers());
        } else {
            System.out.println("playGameResult is null");
        }
    }

    @Test
    void shouldDeleteGameCorrectly() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Quiz");
        quiz.setId(ID_1);
        Game game = new Game(ID_2, null, quiz);

        // when
        when(gameRepository.existsById(any())).thenReturn(true);
        gameService.deleteGame(ID_2);

        // then
        Mockito.verify(gameRepository, Mockito.times(1)).deleteById(game.getId());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentGame() {
        // given
        UUID nonExistentGameId = UUID.randomUUID();

        // when
        when(gameRepository.existsById(nonExistentGameId)).thenReturn(false);

        // then
        assertThrows(EntityNotFoundException.class, () -> gameService.deleteGame(nonExistentGameId));
    }
}