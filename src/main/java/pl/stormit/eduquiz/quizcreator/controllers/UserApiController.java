package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")

public class UserApiController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<List<UserDto>> getUsers(final UUID id, final String nickname,
                                           @RequestBody List<Quiz> quizzes, @RequestHeader("The list of users has been successfully found") String message) {
        return ResponseEntity.ok(userService.getUsers(id,nickname, quizzes, message));
    }

    @GetMapping("{userId}")
    ResponseEntity<UserDto> getUser(@NotNull @PathVariable UUID userId) {
        UserDto foundUserDto = userService.getUser(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "User has been successfully found");
        return new ResponseEntity<>(foundUserDto, headers, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserDto createdUser = userService.createUser(userRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "User has been successfully created");
        return new ResponseEntity<>(createdUser, headers, HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    ResponseEntity<UserDto> updateUser(@NotNull @PathVariable UUID userId,
                                       @Valid @RequestBody UserRequestDto userRequestDto) {
        UserDto updatedUser = userService.updateUser(userId, userRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "User has been successfully updated");
        return new ResponseEntity<>(updatedUser, headers, HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "User has been successfully deleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
