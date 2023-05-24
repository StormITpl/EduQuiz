package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import lombok.Builder;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

import java.util.UUID;

@Builder
public record AnswerDto(UUID id,
                        String content,
                        boolean isCorrect,
                        Question question) {
}
