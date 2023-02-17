package pl.stormit.eduquiz.quizcreator.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void shouldGetUsers() {
        //given
        userRepository.deleteAll();
        userRepository.saveAll(List.of(
                new User("Ananiasz"), new User("Wojski"), new User("Dajmiech")));

        //when
        List<User> userList = userService.getUsers();

        //then
        assertThat(userList)
                .hasSize(3)
                .extracting(User::getNickname)
                .containsExactlyInAnyOrder("Ananiasz", "Wojski", "Dajmiech");
    }

    @Test
    void shouldGetSingleUser() {
        //given
        User user = new User("Wojski");
        userRepository.saveAll(List.of(
                new User("Ananiasz"), user, new User("Dajmiech")));

        //when
        User resultOfSingleUser = userService.getUser(user.getId());

        //then
        assertThat(resultOfSingleUser.getId()).isEqualTo(user.getId());
    }

    @Test
    void shouldCreateUser() {
        //given
        User user = new User("Łamignat");

        //when
        User resultOfCreatingUser = userService.createUser(user);

        //then
        assertThat(resultOfCreatingUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(resultOfCreatingUser.getNickname()).isEqualTo(userRepository.getReferenceById(resultOfCreatingUser.getId()).getNickname());
    }

    @Test
    void shouldUpdateUser() {
        //given
        User user = new User("Woj Wit");
        userRepository.save(user);
        User userRequest = userService.getUser(user.getId());
        user.setNickname("Fochna");

        //when
        User resultOfUpadateUser = userService.updateUser(userRequest.getId(), userRequest);

        //then
        assertThat(resultOfUpadateUser).isEqualTo(userRepository.getReferenceById(userRequest.getId()));
        assertThat(resultOfUpadateUser.getId()).isEqualTo(userRepository.getReferenceById(userRequest.getId()).getId());
        assertThat(resultOfUpadateUser.getNickname()).isEqualTo(userRepository.getReferenceById(userRequest.getId()).getNickname());
    }

    @Test
    void shouldDeleteUser() {
        //given
        User user = new User("Łamignat");
        User userRequest = userService.createUser(user);

        //when
        userService.deleteUser(userRequest.getId());

        //then
        assertThat(userRepository.findById(userRequest.getId())).isEmpty();
    }
}