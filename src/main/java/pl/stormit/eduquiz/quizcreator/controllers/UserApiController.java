package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")

public class UserApiController {

    private final UserService userService;

    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("{id}")
    User getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PostMapping
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userRequest) {
        UserDto createdUser = userService.createUser(userRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userRequest) {
        UserDto updateUser = userService.createUser(userRequest);
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
