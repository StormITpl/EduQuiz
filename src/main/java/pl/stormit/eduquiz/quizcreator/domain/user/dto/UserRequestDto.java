package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.user.Role;
import pl.stormit.eduquiz.quizcreator.domain.user.Status;

import java.time.Instant;
import java.util.List;

public record UserRequestDto(@NotBlank(message = "Nickname must not be blank")
                             @Size(min = 3, max = 13)
                             String nickname,
                             @NotBlank(message = "Email must not be blank")
                             @Email(message = "Invalid email address")
                             String email,
                             @NotBlank(message = "Password must not be blank\n" +
                                     "It must have at least 8 characters and at most 20 characters\n" +
                                     "It must have at least one digit\n" +
                                     "It must have at least one upper case alphabet\n" +
                                     "It must have at least one lower case alphabet\n" +
                                     "It must have at least one special character like !@#$%*&()-+=^\n" +
                                     "It doesn`t contain any white space")
                             @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%*&()-+=^])(?=\\S+$).{8,20}$")
                             String password,
                             Status status,
                             Role role,
                             Instant createdAt,
                             List<Quiz> quizzes) {
}
