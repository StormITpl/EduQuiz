package pl.stormit.eduquiz.result.service;

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
import pl.stormit.eduquiz.game.dto.GameIdDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.domain.repository.ResultRepository;
import pl.stormit.eduquiz.result.dto.ResultDto;
import pl.stormit.eduquiz.result.dto.ResultMapper;
import pl.stormit.eduquiz.statistic.quizstatistic.QuizStatisticFacadeImp;

import java.util.UUID;

@Validated
@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final GameRepository gameRepository;
    private final ResultMapper resultMapper;
    private final QuizStatisticFacadeImp quizStatistic;


    @Transactional(readOnly = true)
    public ResultDto getResult(@NotNull @PathVariable UUID id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Result by id: " + id + " not found");
                });
        return resultMapper.mapResultEntityToResultDto(result);
    }


    @Transactional
    public ResultDto createResult(@Valid @RequestBody GameIdDto gameIdDto) {
        Game game = gameRepository.findById(gameIdDto.id()).orElseThrow(() -> new EntityNotFoundException("The game does not exist with ID: " + gameIdDto.id()));
        Result result = new Result();
        result.setGame(game);
        Long score = game.getQuiz().getQuestions().stream()
                .flatMap(question -> question.getAnswers().stream())
                .filter(Answer::isCorrect)
                .map(Answer::getId)
                .filter(correctId -> game.getUserAnswers().contains(correctId))
                .count();

        result.setScore(score.intValue());

        quizStatistic.addStatisticToDB(game, result.getScore());

        return resultMapper.mapResultEntityToResultDto(resultRepository.save(result));
    }

    @Transactional
    public void deleteResult(@NotNull @PathVariable("result-id") UUID resultId) {
        if (resultRepository.existsById(resultId)) {
            resultRepository.deleteById(resultId);
        } else {
            throw new EntityNotFoundException("The result by id: " + resultId + " does not exist.");
        }
    }
}

