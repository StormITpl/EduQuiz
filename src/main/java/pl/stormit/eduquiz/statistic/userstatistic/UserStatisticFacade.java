package pl.stormit.eduquiz.statistic.userstatistic;

import java.time.Instant;
import java.util.UUID;

public interface UserStatisticFacade {

    long getTotalNumberOfUsers();

    long getNewUsersCountLast30Days();

    Instant lastLoginByUser(UUID userId);

    int getNumberOfLogins(UUID userId);

    void incrementLoginCount(String username);
}
