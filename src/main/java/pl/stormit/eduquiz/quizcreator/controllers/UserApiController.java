package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("{id}")
    UserDto getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PostMapping
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        UserDto updateUser = userService.createUser(userDto);
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}


