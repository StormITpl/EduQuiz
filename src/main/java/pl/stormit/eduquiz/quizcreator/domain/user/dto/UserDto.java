package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.user.Role;
import pl.stormit.eduquiz.quizcreator.domain.user.Status;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record UserDto(UUID id,
                      String nickname,
                      String email,
                      String password,
                      Status status,
                      Role role,
                      Instant createdAt,
                      List<Quiz> quizzes) {
}
