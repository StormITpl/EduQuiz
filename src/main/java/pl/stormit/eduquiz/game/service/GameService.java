package pl.stormit.eduquiz.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.WebApplicationContext;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.dto.GameMapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class GameService {
    private final QuizRepository quizRepository;

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    Game playGame;

    List<UUID> userAnswers = new ArrayList<>();

    public GameDto createGame(QuizDto quizRequest) {
        Quiz quiz = quizRepository.findById(quizRequest.id()).orElseThrow(() -> {
            throw new RuntimeException("The quiz does not exist");
        });
        Game game = new Game(quiz);
        game.setUserAnswers(userAnswers);

        return gameMapper.mapGameEntityToGameDto(gameRepository.save(game));
    }

    public GameDto playGame(UUID gameId, AnswerDto answer) {
        playGame = gameRepository.findById(gameId)
                .orElseThrow(() -> {
                    throw new ResourceAccessException("The quiz does not exist");
                });

        userAnswers.add(answer.id());
        playGame.setUserAnswers(userAnswers);

        return gameMapper.mapGameEntityToGameDto(playGame);
    }

    public GameDto completeGame(UUID gameId) {
        Game updateGame = gameRepository.findById(gameId)
                .orElseThrow(() -> {
                    throw new ResourceAccessException("The game does not exist");
                });
        updateGame.setUserAnswers(playGame.getUserAnswers());

        return gameMapper.mapGameEntityToGameDto(gameRepository.save(updateGame));
    }

    public void deleteGame(UUID gameId) {
        gameRepository.deleteById(gameId);
    }
}