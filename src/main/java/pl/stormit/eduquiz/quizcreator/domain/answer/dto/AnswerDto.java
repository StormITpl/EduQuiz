package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import pl.stormit.eduquiz.quizcreator.domain.question.Question;

import java.util.UUID;

public record AnswerDto(UUID id,
                        String content,
                        boolean isCorrect,
                        Question question) {
}
