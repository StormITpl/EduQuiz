package pl.stormit.eduquiz.quizcreator.domain.question.dto;

import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;
import java.util.UUID;
public record QuestionDto(UUID id,
                          String content,
                          String correctAnswer,
                          Quiz quiz,
                          List<Answer> answers) {
}