package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;
import java.util.UUID;

public record UserDto(UUID id, String nickname, List<Quiz> quizzes) {
}
