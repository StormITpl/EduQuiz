package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;

public record UserRequestDto(String nickname, List<Quiz> quizzes) {
}
