package pl.stormit.eduquiz.quizcreator.domain.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.stormit.eduquiz.authenticator.CustomPasswordEncoder;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserMapper;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final CustomPasswordEncoder customPasswordEncoder;

    private final UserXlsExporterService exportUsersToXLS;

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        return getUsers(null, null);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(String search, String searchMail) {
        List<User> foundUsers;
        if (search != null) {
            foundUsers = userRepository.findByNicknameContainingIgnoreCase(search)
                    .orElseThrow(() -> new EntityNotFoundException("Users by search: " + search + " do not exist."));
        } else if (searchMail != null) {
            foundUsers = userRepository.findByEmailContainingIgnoreCase(searchMail)
                    .orElseThrow(() -> new EntityNotFoundException("Users by searchMail: " + searchMail + " do not exist."));
        } else {
            foundUsers = userRepository.findAll();
        }
        return userMapper.mapUserListOfEntityToUsersDtoList(foundUsers);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(@NotNull @PathVariable("user-id") UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException("User by id: " + userId + " does not exist.");
        });
        return userMapper.mapUserEntityToUserDto(user);
    }

    public User getUserFromContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByNickname(authentication.getName()).orElse(null);
        return user;
    }

    @Transactional
    public UserDto createUser(@Valid @RequestBody UserRequestDto userRequest) {
        User user = new User();
        user.setNickname(userRequest.nickname());
        user.setEmail(userRequest.email());
        user.setPassword(customPasswordEncoder.encode(userRequest.password()));
        user.setStatus(Status.UNVERIFIED);
        user.setRole(Role.ROLE_USER);
        return userMapper.mapUserEntityToUserDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateUser(@NotNull @PathVariable("user-id") UUID userId,
                              @Valid @RequestBody UserRequestDto userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new EntityNotFoundException("User by id: " + userId + " does not exist.");
        });
        user.setNickname(userRequest.nickname());
        user.setEmail(userRequest.email());
        user.setPassword(customPasswordEncoder.encode(userRequest.password()));
        user.setStatus(userRequest.status());
        user.setRole(userRequest.role());
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

    public byte[] exportUsersToXLS() throws IOException {
        List<User> foundUsers = userRepository.findAll();
        return exportUsersToXLS.exportUsersToXLS(foundUsers);
    }

    public boolean comparePasswords(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    public boolean checkIfNicknameAvailable(UserRequestDto userRequestDto) {
        return userRepository.findUserByNickname(userRequestDto.nickname()).isPresent();
    }

    @Transactional(readOnly = true)
    public long getTotalNumberOfUsers() {
        return userRepository.count();
    }

    @Transactional(readOnly = true)
    public long getNewUsersCountLast30Days() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        ZonedDateTime zonedDateTime = thirtyDaysAgo.atStartOfDay(ZoneId.systemDefault());
        return userRepository.countNewUsersLast30Days(zonedDateTime.toInstant());
    }
}
