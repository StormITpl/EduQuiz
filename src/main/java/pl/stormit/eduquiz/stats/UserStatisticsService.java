package pl.stormit.eduquiz.stats;

import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserStatisticsService {

    private static final String TOTAL_USERS_KEY = "totalUsers";
    private static final String USERS_REGISTERED_LAST_WEEK_KEY = "usersRegisteredLastWeek";

    private final UserRepository userRepository;

    public UserStatisticsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long getTotalNumberOfUsers() {
        return userRepository.count();
    }

    public long getNumberOfUsersCreatedBetween(Instant startDate, Instant endDate) {
        return userRepository.countByCreatedAtBetween(startDate, endDate);
    }

    public long getUsersRegisteredLastWeek() {
        Instant weekAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        return getNumberOfUsersCreatedBetween(weekAgo, Instant.now());
    }

    public Map<String, Long> getUserStatistics() {
        Map<String, Long> statistics = new HashMap<>();
        statistics.put(TOTAL_USERS_KEY, getTotalNumberOfUsers());
        Instant weekAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        statistics.put(USERS_REGISTERED_LAST_WEEK_KEY, getNumberOfUsersCreatedBetween(weekAgo, Instant.now()));
        return statistics;
    }
}