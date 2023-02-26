package pl.stormit.eduquiz.result.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.domain.repository.ResultRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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
        List<UUID> userAnswers = game.getUserAnswers();
        List<Question> questions = quiz.getQuestions();
        int goodAnswers = 0;

        for(Question question : questions) {
            for(Answer answer : question.getAnswers()) {
                if(answer.isCorrect() && userAnswers.contains(answer.getId())) {
                    goodAnswers++;
                }
            }
        }

        return goodAnswers;
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

    @Transactional
    public void deleteResult(UUID id) {
        resultRepository.deleteById(id);
    }
}
