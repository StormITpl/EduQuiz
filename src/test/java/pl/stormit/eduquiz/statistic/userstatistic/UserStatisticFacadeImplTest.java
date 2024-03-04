package pl.stormit.eduquiz.statistic.userstatistic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStatisticFacadeImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserStatisticFacadeImpl userStatisticFacade;

    @Test
    void getTotalNumberOfUsersTest() {
        // given
        long expectedTotalUsers = 5L;
        when(userService.getTotalNumberOfUsers()).thenReturn(expectedTotalUsers);

        // when
        long actualTotalUsers = userStatisticFacade.getTotalNumberOfUsers();

        // then
        assertEquals(expectedTotalUsers, actualTotalUsers, "The total number of users should match the expected value");
    }

    @Test
    void shouldReturnCorrectNumberOfNewUsers() {
        // given
        long expectedCount = 10L;
        when(userService.getNewUsersCountLast30Days()).thenReturn(expectedCount);

        // when
        long actualCount = userStatisticFacade.getNewUsersCountLast30Days();

        // then
        assertEquals(expectedCount, actualCount, "Expected number of new users in the last 30 days should be " + expectedCount + " but got " + actualCount);
    }

    @Test
    void shouldReturnZeroWhenNoNewUsers() {
        // given
        when(userService.getNewUsersCountLast30Days()).thenReturn(0L);

        // when
        long actualCount = userStatisticFacade.getNewUsersCountLast30Days();

        // then
        assertEquals(0, actualCount, "Expected number of new users in the last 30 days should be 0 but got " + actualCount);
    }
}
