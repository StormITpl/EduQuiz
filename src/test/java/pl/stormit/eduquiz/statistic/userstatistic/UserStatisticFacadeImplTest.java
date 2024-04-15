package pl.stormit.eduquiz.statistic.userstatistic;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.stormit.eduquiz.quizcreator.domain.user.Role;
import pl.stormit.eduquiz.quizcreator.domain.user.Status;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserStatisticFacadeImplTest {

    private static final UUID USER_ID = UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82501");
    private static final UUID USER_STATISTIC_ID = UUID.fromString("3991da85-80dc-4080-a78e-517ee80547d3");
    private static final UUID NON_EXISTENT_USER_ID = UUID.fromString("a3cbdcef-781e-4de3-9662-be374af97a4d");
    public static final String NICKNAME = "Johny Deep";
    public static final String EMAIL = "deep@gmail.com";
    public static final String PASSWORD = "1234";
    public static final Instant CREATED_AT = Instant.parse("2023-02-15T18:35:24.00Z");
    public static final Instant LAST_LOGIN_DATE = Instant.parse("2023-03-10T19:35:24.00Z");

    @Mock
    private UserService userService;

    @Mock
    private UserStatisticRepository userStatisticRepository;

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

    @Test
    void shouldReturnLastLoginDateByUserWhenUserExist() {
        // given
        User user = createTestUser();
        UserStatistic userStatistic = new UserStatistic(
                USER_STATISTIC_ID,
                user,
                LAST_LOGIN_DATE,
                10,
                0,
                0);

        when(userStatisticRepository.findById(any(UUID.class))).thenReturn(Optional.of(userStatistic));

        // when
        Instant resultLastLogin = userStatisticFacade.lastLoginByUser(USER_ID);

        // then
        assertEquals(LAST_LOGIN_DATE, resultLastLogin);
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        // given
        UUID nonExistentUserId = NON_EXISTENT_USER_ID;

        when(userStatisticRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> userStatisticFacade.lastLoginByUser(nonExistentUserId));
    }

    @Test
    void shouldReturnDefaultLoginDateByUserWhenUserHasNotLoggedInForFirstTime() {
        // given
        User user = createTestUser();

        UserStatistic userStatistic = new UserStatistic(
                USER_ID,
                user,
                null,
                0,
                0,
                0);

        when(userStatisticRepository.findById(USER_ID)).thenReturn(Optional.of(userStatistic));

        // when
        Instant resultLastLogin = userStatisticFacade.lastLoginByUser(USER_ID);

        // then
        assertNull(resultLastLogin);
    }

    private User createTestUser() {
        return new User(
                USER_ID,
                NICKNAME,
                EMAIL,
                PASSWORD,
                Status.VERIFIED,
                Role.ROLE_USER,
                CREATED_AT,
                null);
    }
}
