package pl.stormit.eduquiz.quizcreator.domain.question.dto;

import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;

public record QuestionRequestDto(String content,
                                 Quiz quiz,
                                 List<Answer> answers) {
}
