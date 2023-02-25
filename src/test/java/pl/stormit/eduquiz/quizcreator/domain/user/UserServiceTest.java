package pl.stormit.eduquiz.quizcreator.domain.user;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserDto;
import pl.stormit.eduquiz.quizcreator.domain.user.dto.UserMapper;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void SetUp() {
        userMapper.mapUserEntityToUsersDtoList(userRepository.saveAll(List.of(
                new User("Ananiasz"),
                new User("Wojski"),
                new User("Dajmiech")
        )));
    }

    @Test
    void shouldGetAllUsers_1() {
        //given
        List<User> users = userRepository.findAll();
        List<UserDto> expectedUsers = userMapper.mapUserEntityToUsersDtoList(users);

        //when
        List<UserDto> actualUsers = userMapper.mapUserEntityToUsersDtoList(userRepository.findAll());

        //then
        Assertions.assertThat(actualUsers).isNotNull();
        Assertions.assertThat(actualUsers).hasSize(expectedUsers.size());
        Assertions.assertThat(actualUsers).extracting(UserDto::nickname);
        Assertions.assertThat(actualUsers).containsExactlyElementsOf(expectedUsers);
    }

    @Test
    void shouldGetAllUsers_2() {
        //given
        userRepository.deleteAll();
        userRepository.saveAll(List.of(
                new User("Ananiasz"), new User("Wojski"), new User("Dajmiech")));

        //when
        List<UserDto> userList = userMapper.mapUserEntityToUsersDtoList(userRepository.findAll());

        //then
        assertThat(userList)
                .hasSize(3)
                .extracting(UserDto::nickname)
                .containsExactlyInAnyOrder("Ananiasz", "Wojski", "Dajmiech");
    }

    @Test
    void shouldGetSingleUser() {
        //given
        User user = new User("Wojski");
        userRepository.saveAll(List.of(
                new User("Ananiasz"), user, new User("Dajmiech")));

        //when
        UserDto resultOfSingleUser = userMapper.mapUserEntityToUserDto(userRepository.getReferenceById(user.getId()));

        //then
        assertThat(resultOfSingleUser.id()).isEqualTo(user.getId());
    }

    @Test
    @Transactional
    @Rollback
    void shouldCreateUser_1() {
        // given
        final UUID ID_1 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d3");
        UserDto userDto = new UserDto(ID_1, "Łamignat");

        // when
        UserDto createdUserDto = userService.createUser(userDto);

        // then
        assertThat(createdUserDto).isNotNull();
        assertThat(createdUserDto.nickname()).isEqualTo(userDto.nickname());

        User createdUser = userRepository.findById(createdUserDto.id()).orElse(null);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getNickname()).isEqualTo(userDto.nickname());
    }

    @Test
    @Transactional
    @Rollback
    void shouldCreateUser_2() {
        //given
        final UUID ID_1 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d3");
        UserDto userDto = new UserDto(ID_1, "Łamignat");

        //when
        UserDto resultOfCreatingUserDto = userService.createUser(userDto);

        //then
        assertThat(resultOfCreatingUserDto.nickname()).isEqualTo(userDto.nickname());
        assertThat(resultOfCreatingUserDto.nickname()).isEqualTo(userRepository.getReferenceById(resultOfCreatingUserDto.id()).getNickname());
    }

    @Test
    @Transactional
    void shouldUpdateUser() {
        //given
        User user = new User();
        user.setNickname("Andrzej Duda");
        userRepository.save(user);

        final UUID ID_1 = UUID.fromString("b9d82a81-c317-4eee-9da7-7680785df4d3");
        UserDto updatedUser = new UserDto(ID_1, "Andrzej Dude");

        //when
        UserDto resultOfUpdateUser = userService.updateUser(user.getId(), updatedUser);

        //then
        assertThat(resultOfUpdateUser.nickname()).isEqualTo(updatedUser.nickname());
        User updatedEntity = userRepository.findById(user.getId()).orElseThrow();
        assertThat(updatedEntity.getNickname()).isEqualTo(updatedUser.nickname());
    }

    @Test
    void shouldDeleteUser() {
        //given
        User user = new User("Oferma");
        user = userRepository.save(user);

        //when
        userRepository.deleteById(user.getId());

        //then
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}