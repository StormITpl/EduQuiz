package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserApiController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> usersDtoList = userService.getUsers();
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The list of users has been successfully found");
        return new ResponseEntity<>(usersDtoList, headers, HttpStatus.OK);
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
    ResponseEntity<Void> deleteUser(@NotNull @PathVariable UUID userId) {
        userService.deleteUser(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "User has been successfully deleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportUsersToXLS() throws IOException {
        byte[] excelBytes = userService.exportUsersToXLS();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "users.xlsx");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}
