package pl.stormit.eduquiz.statistic.userstatistic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserStatisticIntegrationTest {

    @Autowired
    private UserStatisticFacade userStatisticFacade;

    @Autowired
    private UserService userService;

    @Test
    void totalNumberOfUsersIntegrationTest() {

        // given

        // when
        long totalUsers = userStatisticFacade.getTotalNumberOfUsers();

        // then
        assertTrue(totalUsers > 0, "The total number of users should be greater than zero");
    }
}
