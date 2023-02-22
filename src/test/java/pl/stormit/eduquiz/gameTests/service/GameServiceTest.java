package pl.stormit.eduquiz.gameTests.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.UUID;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
public class GameServiceTest {

    private static final UUID ID_1 = UUID.fromString("5d1b4c2c-9f1c-11ed-a8fc-0242ac120002");

    @MockBean
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Test
    void shouldDeleteGame() {
        Quiz quiz = new Quiz("Quiz");
        quiz.setId(ID_1);
        Game game = new Game(ID_1, null, quiz);

        Mockito.doNothing().when(gameRepository).deleteById(game.getId());

        gameService.deleteGame(game.getId());
        Mockito.verify(gameRepository, Mockito.times(1)).deleteById(game.getId());
        Mockito.verifyNoMoreInteractions(gameRepository);
    }
}
