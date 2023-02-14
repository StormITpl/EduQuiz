package pl.stormit.eduquiz.game.dto;

import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;

import java.util.List;

public record GameDto(
        List<Answer> userAnswers,
        Quiz quiz
) {}
