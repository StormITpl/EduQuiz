package pl.stormit.eduquiz.quizcreator.domain.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.authenticator.CustomPasswordEncoder;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserMapper;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final CustomPasswordEncoder customPasswordEncoder;

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        List<User> foundUsers = userRepository.findAll();
        return userMapper.mapUserListOfEntityToUsersDtoList(foundUsers);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException("User by id: " + userId + " does not exist.");
        });
        return userMapper.mapUserEntityToUserDto(user);
    }

    @Transactional
    public UserDto createUser(@NotNull UserRequestDto userRequest) {
        User user = new User();
        user.setNickname(userRequest.nickname());
        user.setEmail(userRequest.email());
        user.setPassword(customPasswordEncoder.encode(userRequest.password()));
        user.setStatus(Status.UNVERIFIED);
        user.setRole(Role.ROLE_USER);
        user.setQuizzes(userRequest.quizzes());
        return userMapper.mapUserEntityToUserDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateUser(@NotNull UUID userId,  @NotNull UserRequestDto userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException("User by id: " + userId + " does not exist.");
        });
        user.setNickname(userRequest.nickname());
        user.setEmail(userRequest.email());
        user.setPassword(customPasswordEncoder.encode(userRequest.password()));
        user.setStatus(userRequest.status());
        user.setRole(userRequest.role());
        user.setQuizzes(userRequest.quizzes());
        User savedUser = userRepository.save(user);
        return userMapper.mapUserEntityToUserDto(savedUser);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User by id: " + userId + " does not exist.");
        }
    }

    public boolean comparePasswords(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    public boolean checkIfNicknameAvailable(UserRequestDto userRequestDto) {
        return userRepository.findByNickname(userRequestDto.nickname()).isPresent();
    }
}
