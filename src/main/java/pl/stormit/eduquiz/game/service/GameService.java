package pl.stormit.eduquiz.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.dto.GameMapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class GameService {
    private final QuizRepository quizRepository;

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    List<UUID> userAnswers = new ArrayList<>();

    public GameDto createGame(QuizDto quizRequest) {
        Quiz quiz = quizRepository.findById(quizRequest.id()).orElseThrow(() -> {
            throw new RuntimeException("The quiz does not exist");
        });
        Game game = new Game(quiz);
        game.setUserAnswers(userAnswers);

        return gameMapper.mapGameEntityToGameDto(gameRepository.save(game));
    }

    public GameDto playGame(UUID gameId, List<AnswerDto> answers) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> {
                    throw new RuntimeException("The quiz does not exist");
                });

        userAnswers = game.getUserAnswers();
        List<UUID> collectedAnswers = answers.stream()
                .map(AnswerDto::id)
                .toList();
        userAnswers.addAll(collectedAnswers);
        game.setUserAnswers(userAnswers);

        return gameMapper.mapGameEntityToGameDto(gameRepository.save(game));
    }

    public void deleteGame(UUID gameId) {
        gameRepository.deleteById(gameId);
    }
}