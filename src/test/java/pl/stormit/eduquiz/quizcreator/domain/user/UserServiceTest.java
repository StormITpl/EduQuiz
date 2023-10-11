package pl.stormit.eduquiz.quizcreator.domain.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.authenticator.CustomPasswordEncoder;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.stormit.eduquiz.quizcreator.domain.user.Status.VERIFIED;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomPasswordEncoder encoder;

    private User firstUser;

    private User secondUser;

    private User thirdUser;

    @BeforeEach
    void SetUp() {
        firstUser = new User();
        firstUser.setNickname("Ananiasz");
        firstUser.setEmail("ananiasz@gmail.com");
        firstUser.setPassword("password");
        firstUser.setStatus(VERIFIED);
        firstUser.setRole(Role.ROLE_ADMIN);
        userRepository.save(firstUser);

        secondUser = new User();
        secondUser.setNickname("Wojski");
        secondUser.setEmail("wojski@gmail.com");
        secondUser.setPassword("password");
        secondUser.setStatus(VERIFIED);
        secondUser.setRole(Role.ROLE_ADMIN);
        userRepository.save(secondUser);

        thirdUser = new User();
        thirdUser.setNickname("Dajmiech");
        thirdUser.setEmail("dajmiech@gmail.com");
        thirdUser.setPassword("password");
        thirdUser.setStatus(VERIFIED);
        thirdUser.setRole(Role.ROLE_ADMIN);
        userRepository.save(thirdUser);
    }

    @Test
    void shouldReturnAllUsers() {
        // given
        List<User> users = userRepository.findAll();
        List<UserDto> expectedUsers = userMapper.mapUserListOfEntityToUsersDtoList(users);

        // when
        List<UserDto> actualUsers = userService.getUsers();

        // then
        assertThat(actualUsers).isNotNull();
        assertThat(actualUsers).hasSize(expectedUsers.size());
        assertThat(actualUsers).containsExactlyElementsOf(expectedUsers);
    }

    @Test
    void shouldGetSingleUser() {
        // given
        UUID firstId = firstUser.getId();

        // when
        UserDto foundUserDto = userService.getUser(firstId);

        // then
        assertThat(foundUserDto.id()).isEqualTo(firstId);
    }

    @Test
    @Transactional
    @Rollback
    void shouldCreateUser() {
        // given
        UserRequestDto userRequestDto = new UserRequestDto(
                "Łamignat",
                "lamignat@gmail.com",
                "password",
                null,
                null,
                null,
                null);

        // when
        UserDto createdUserDto = userService.createUser(userRequestDto);

        // then
        assertThat(createdUserDto.nickname()).isEqualTo(userRequestDto.nickname());
        assertThat(createdUserDto.email()).isEqualTo(userRequestDto.email());
        assertTrue(encoder.matches(userRequestDto.password(), createdUserDto.password()));
        assertThat(createdUserDto.quizzes()).isNull();
    }

    @Test
    @Transactional
    void shouldUpdateUser() {
        // given
        UUID firstId = firstUser.getId();
        Quiz quiz = new Quiz();
        quiz.setName("Automotive");
        quizRepository.save(quiz);
        List<Quiz> quizzesList = new ArrayList<>();
        quizzesList.add(quiz);
        UserRequestDto userRequestDto = new UserRequestDto(
                "Gniewosz",
                "gniewosz@gmail.com",
                "password",
                null,
                null,
                null,
                quizzesList);

        // when
        UserDto updatedUserDto = userService.updateUser(firstId, userRequestDto);

        // then
        assertThat(updatedUserDto.nickname()).isEqualTo("Gniewosz");
        assertThat(updatedUserDto.email()).isEqualTo("gniewosz@gmail.com");
        assertTrue(encoder.matches(userRequestDto.password(), updatedUserDto.password()));
        assertThat(updatedUserDto.id()).isEqualTo(firstId);
        assertThat(updatedUserDto.quizzes()).isEqualTo(quizzesList);
    }

    @Test
    void shouldDeleteUser() {
        // given
        User user = new User();
        user.setNickname("Oferma");
        User savedUser = userRepository.save(user);

        // when
        userRepository.deleteById(savedUser.getId());

        // then
        assertThat(userRepository.findById(user.getId())).isEmpty();
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(savedUser.getId()));
    }
}
