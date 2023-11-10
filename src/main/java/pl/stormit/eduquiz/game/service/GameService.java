package pl.stormit.eduquiz.game.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

@Validated
@Service
@RequiredArgsConstructor
public class GameService {

    private final QuizRepository quizRepository;

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private Game playGame;

    List<UUID> userAnswers = new ArrayList<>();

    @Transactional
    public GameDto createGame(@Valid @RequestBody QuizDto quizRequestDto) {
        Quiz quiz = quizRepository.findById(quizRequestDto.id()).orElseThrow(() -> {
            throw new EntityNotFoundException("The quiz by id: " + quizRequestDto.id() + ", does not exist.");
        });
        Game game = new Game(quiz);
        game.setStart();
        game.setUserAnswers(userAnswers);

        return gameMapper.mapGameEntityToGameDto(gameRepository.save(game));
    }

    @Transactional(readOnly = true)
    public GameDto getGame(@NotNull @PathVariable UUID gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> {
            throw new EntityNotFoundException("The game by id: " + gameId + ", does not exist.");
        });

        return gameMapper.mapGameEntityToGameDto(game);
    }

    public GameDto playGame(@NotNull @PathVariable UUID gameId,
                            @Valid @RequestBody AnswerDto answer) {
        playGame = gameRepository.findById(gameId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("The game by id: " + gameId + ", does not exist.");
                });
        userAnswers.add(answer.id());
        playGame.setUserAnswers(userAnswers);

        return gameMapper.mapGameEntityToGameDto(playGame);
    }

    @Transactional
    public GameDto completeGame(@NotNull @PathVariable UUID gameId) {
        Game updateGame = gameRepository.findById(gameId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("The game by id: " + gameId + ", does not exist.");
                });
        updateGame.setUserAnswers(playGame.getUserAnswers());
        updateGame.setFinish();

        return gameMapper.mapGameEntityToGameDto(updateGame);
    }

    @Transactional
    public void deleteGame(@NotNull @PathVariable UUID gameId) {
        if (gameRepository.existsById(gameId)) {
            gameRepository.deleteById(gameId);
        } else {
            throw new EntityNotFoundException("The game by id: " + gameId + ", does not exist.");
        }
    }
}
