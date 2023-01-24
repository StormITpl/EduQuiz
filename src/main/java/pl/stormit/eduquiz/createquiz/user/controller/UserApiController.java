package pl.stormit.eduquiz.createquiz.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.createquiz.user.domain.model.User;
import pl.stormit.eduquiz.createquiz.user.service.UserService;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    User createQuiz(@RequestBody User user) {
        return userService.createQuiz(user);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("{id}")
    User updateQuiz(@PathVariable UUID id, @RequestBody User user) {
        return userService.updateQuiz(id, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void deleteQuiz(@PathVariable UUID id) {
        userService.deleteQuiz(id);
    }
}
