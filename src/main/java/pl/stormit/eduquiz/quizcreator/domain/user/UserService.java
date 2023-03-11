package pl.stormit.eduquiz.quizcreator.domain.user;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        return userMapper.mapUserEntityToUsersDtoList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UserDto getUser(UUID id) {
        return userMapper.mapUserEntityToUserDto(userRepository.findById(id)
                .orElseThrow());
    }

    @Transactional
    public UserDto createUser(@NotNull UserDto userRequest) {
        User user = new User();
        user.setNickname(userRequest.nickname());
        return userMapper.mapUserEntityToUserDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateUser(UUID id, @NotNull UserDto userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow();
        user.setNickname(userRequest.nickname());
        return userMapper.mapUserEntityToUserDto(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}