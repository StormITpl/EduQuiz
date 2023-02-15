package pl.stormit.eduquiz.result.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.domain.repository.ResultRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    private final GameRepository gameRepository;

    @Transactional(readOnly = true)
    public Result getResult(UUID id) {
        return resultRepository.getById(id);
    }

    private Integer getScore(Game game, Quiz quiz) {
        return 0;
    }

    @Transactional
    public Result createResult(@NotNull Game game, Quiz quiz) {
        Result result = new Result();
        result.setId(game.getId());
        result.setGame(game);
        result.setQuiz(quiz);
        int score = this.getScore(game, quiz);
        result.setScore(score);

        return resultRepository.save(result);
    }
}
