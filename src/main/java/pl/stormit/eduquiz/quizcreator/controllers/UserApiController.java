package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserApiController {

    private final UserService userService;

    private static final String MSG = "message";

    @GetMapping
    ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> usersDtoList = userService.getUsers();
        HttpHeaders headers = new HttpHeaders();
        headers.add(MSG, "The list of users has been successfully found");
        return new ResponseEntity<>(usersDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    ResponseEntity<UserDto> getUser(@PathVariable UUID userId) {
        UserDto foundUserDto = userService.getUser(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(MSG, "User has been successfully found");
        return new ResponseEntity<>(foundUserDto, headers, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserDto createdUser = userService.createUser(userRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(MSG, "User has been successfully created");
        return new ResponseEntity<>(createdUser, headers, HttpStatus.CREATED);
    }

    @PutMapping("{userId}")
    ResponseEntity<UserDto> updateUser(@PathVariable UUID userId,
                                       @Valid @RequestBody UserRequestDto userRequestDto) {
        UserDto updatedUser = userService.updateUser(userId, userRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(MSG, "User has been successfully updated");
        return new ResponseEntity<>(updatedUser, headers, HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(MSG, "User has been successfully deleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/statistics")
    ResponseEntity<Map<String, Long>> getUserStatistics() {
        Map<String, Long> statistics = new HashMap<>();
        statistics.put("totalUsers", userService.getTotalNumberOfUsers());
        Instant weekAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        statistics.put("usersRegisteredLastWeek", userService.getNumberOfUsersCreatedBetween(weekAgo, Instant.now()));

        HttpHeaders headers = new HttpHeaders();
        headers.add(MSG, "User statistics have been successfully retrieved");
        return new ResponseEntity<>(statistics, headers, HttpStatus.OK);
    }
}
