package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.user.Role;
import pl.stormit.eduquiz.quizcreator.domain.user.Status;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public record UserRequestDto(String nickname,
                             String email,
                             String password,
                             Status status,
                             Role role,
                             Instant createdAt,
                             List<Quiz> quizzes) {
}
