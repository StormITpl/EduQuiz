package pl.stormit.eduquiz.result.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.dto.GameIdDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.domain.repository.ResultRepository;
import pl.stormit.eduquiz.result.dto.ResultDto;
import pl.stormit.eduquiz.result.dto.ResultMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final GameRepository gameRepository;
    private final ResultMapper resultMapper;


    @Transactional(readOnly = true)
    public ResultDto getResult(UUID id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Result not found");
                });
        return resultMapper.mapResultEntityToResultDto(result);
    }

    private Integer getScore(Game game) {
        List<UUID> userAnswersIds = game.getUserAnswers();
        Quiz quiz = game.getQuiz();
        List<Question> questions = quiz.getQuestions();
        int correctAnswers = 0;
        correctAnswers = countCorrectAnswers(userAnswersIds, questions, correctAnswers);
        return correctAnswers;
    }

    private int countCorrectAnswers(List<UUID> userAnswersIds, List<Question> questions, int correctAnswers) {
        for (Question question : questions) {
            for (Answer answer : question.getAnswers()) {
                if (answer.isCorrect() && userAnswersIds.contains(answer.getId())) {
                    correctAnswers++;
                }
            }
        }
        return correctAnswers;
    }

    @Transactional
    public ResultDto createResult(@NotNull GameIdDto gameIdDto) {
        Game game = gameRepository.findById(gameIdDto.id()).orElseThrow(() -> {
            throw new EntityNotFoundException("The game does not exist with ID: " + gameIdDto);
        });
        Result result = new Result();
        result.setGame(game);
        Long score = game.getQuiz().getQuestions().stream()
                .flatMap(question -> question.getAnswers().stream())
                .filter(Answer::isCorrect)
                .map(Answer::getId)
                .filter(correctId -> game.getUserAnswers().contains(correctId))
                .count();


        result.setScore(score.intValue());

        return resultMapper.mapResultEntityToResultDto(resultRepository.save(result));
    }

    @Transactional
    public void deleteResult(UUID resultId) {
        if (resultRepository.existsById(resultId)) {
            resultRepository.deleteById(resultId);
        } else {
            throw new EntityNotFoundException("The result by id: " + resultId + " does not exist.");
        }
    }
}

