package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import java.util.UUID;

public record AnswerDto(UUID id, String content,
                        boolean isCorrect) {
}
