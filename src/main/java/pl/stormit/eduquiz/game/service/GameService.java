package pl.stormit.eduquiz.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.dto.GameMapper;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final QuizRepository quizRepository;

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;


    public GameDto createGame(QuizDto quizRequest) {
        Quiz quiz = quizRepository.findById(quizRequest.id()).orElseThrow(() -> {
            throw new RuntimeException("The quiz does not exist");
        });
        Game game = new Game(quiz);

        return gameMapper.mapGameEntityToGameDto(gameRepository.save(game));
    }

    public void deleteGame(UUID gameId) {
        gameRepository.deleteById(gameId);
    }
}