package pl.stormit.eduquiz.statistic.userstatistic;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class UserStatisticFacadeImpl implements UserStatisticFacade {
    private final UserService userService;

    private final UserStatisticRepository userStatisticRepository;

    @Override
    public long getTotalNumberOfUsers() {
        return userService.getTotalNumberOfUsers();
    }

    @Override
    public long getNewUsersCountLast30Days() {
        return userService.getNewUsersCountLast30Days();
    }

    @Override
    public Instant lastLoginByUser(UUID userId) {
        UserStatistic userStatistics = userStatisticRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("The user statistics by id: " + userId + ", does not exist."));
        return userStatistics.getLastLoginDate();
    }

    @Override
    public int getNumberOfLogins(UUID userId) {
        UserStatistic userStatistic = userStatisticRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("The user statistics by id: " + userId + ", does not exist."));
        return userStatistic.getLoginCount();
    }

    @Override
    public void incrementLoginCount(String username) {
        UserStatistic userStatistic = userStatisticRepository.findByUserNickname(username)
                .orElseThrow(() -> new EntityNotFoundException("The user by nickname: " + username + ", does not exist."));
        userStatistic.setLoginCount(userStatistic.getLoginCount() + 1);
        userStatisticRepository.save(userStatistic);
    }
}