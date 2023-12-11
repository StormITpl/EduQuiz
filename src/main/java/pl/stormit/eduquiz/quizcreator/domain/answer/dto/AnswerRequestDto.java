package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import lombok.Builder;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

@Builder
public record AnswerRequestDto(String content,
                               //boolean isCorrect,
                               Question question) {
}
