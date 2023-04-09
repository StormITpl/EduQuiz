package pl.stormit.eduquiz.game.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@RequiredArgsConstructor
public class GameService {

    private final QuizRepository quizRepository;

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private Game playGame;

    List<UUID> userAnswers = new ArrayList<>();

    @Transactional
    public GameDto createGame(QuizDto quizRequestDto) {
        Quiz quiz = quizRepository.findById(quizRequestDto.id()).orElseThrow(() -> {
            throw new EntityNotFoundException("The quiz by id: " + quizRequestDto.id() + ", does not exist.");
        });
        Game game = new Game(quiz);
        game.setUserAnswers(userAnswers);

        return gameMapper.mapGameEntityToGameDto(game);
    }

    @Transactional(readOnly = true)
    public GameDto getGame(UUID gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> {
            throw new EntityNotFoundException("The game by id: " + gameId + ", does not exist.");
        });
        return gameMapper.mapGameEntityToGameDto(game);
    }

    public GameDto playGame(UUID gameId, AnswerDto answer) {
        playGame = gameRepository.findById(gameId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("The game by id: " + gameId + ", does not exist.");
                });

        userAnswers.add(answer.id());
        playGame.setUserAnswers(userAnswers);

        return gameMapper.mapGameEntityToGameDto(playGame);
    }

    @Transactional
    public GameDto completeGame(UUID gameId) {
        Game updateGame = gameRepository.findById(gameId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("The game by id: " + gameId + ", does not exist.");
                });
        updateGame.setUserAnswers(playGame.getUserAnswers());

        return gameMapper.mapGameEntityToGameDto(updateGame);
    }

    @Transactional
    public void deleteGame(UUID gameId) {
        if (gameRepository.existsById(gameId)) {
            gameRepository.deleteById(gameId);
        } else {
            throw new EntityNotFoundException("The game by id: " + gameId + ", does not exist.");
        }
    }
}