package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import pl.stormit.eduquiz.quizcreator.domain.question.Question;

public record AnswerRequestDto(String content,
                               boolean isCorrect,
                               Question question) {
}
