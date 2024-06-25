package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import pl.stormit.eduquiz.quizcreator.domain.user.Role;
import pl.stormit.eduquiz.quizcreator.domain.user.Status;

import java.time.Instant;

public record UserRequestDto(@NotBlank(message = "Nickname must not be blank")
                             @Size(min = 3, max = 13)
                             String nickname,
                             @NotBlank(message = "Email must not be blank")
                             @Email(message = "Invalid email address")
                             String email,

                             @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%*&()-+=^])(?=\\S+$).{8,20}$",
                                     message = "<ul><li>Password must not be blank</li>" +
                                             "<li>It must have at least 8 characters and at most 20 characters</li>" +
                                             "<li>It must have at least one digit</li>" +
                                             "<li>It must have at least one upper case alphabet</li>" +
                                             "<li>It must have at least one lower case alphabet</li>" +
                                             "<li>It must have at least one special character like !@#$%*&()-+=^</li>" +
                                             "<li>It doesn`t contain any white space</li></ul>")
                             String password,
                             Status status,
                             Role role,
                             Instant createdAt) {
}
