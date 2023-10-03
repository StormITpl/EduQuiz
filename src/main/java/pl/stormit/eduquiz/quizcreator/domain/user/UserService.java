package pl.stormit.eduquiz.quizcreator.domain.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserMapper;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        List<User> foundUsers = userRepository.findAll();
        return userMapper.mapUserListOfEntityToUsersDtoList(foundUsers);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(@NotNull @PathVariable("user-id") UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException("User by id: " + userId + " does not exist.");
        });
        return userMapper.mapUserEntityToUserDto(user);
    }

    @Transactional
    public UserDto createUser(@Valid @RequestBody UserRequestDto userRequest) {
        User user = new User();
        user.setNickname(userRequest.nickname());
        user.setQuizzes(userRequest.quizzes());
        return userMapper.mapUserEntityToUserDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateUser(@NotNull @PathVariable("user-id") UUID userId,
                              @Valid @RequestBody UserRequestDto userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException("User by id: " + userId + " does not exist.");
        });
        user.setNickname(userRequest.nickname());
        user.setQuizzes(userRequest.quizzes());
        User savedUser = userRepository.save(user);
        return userMapper.mapUserEntityToUserDto(savedUser);
    }

    @Transactional
    public void deleteUser(@NotNull @PathVariable("user-id") UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User by id: " + userId + " does not exist.");
        }
    }
}
