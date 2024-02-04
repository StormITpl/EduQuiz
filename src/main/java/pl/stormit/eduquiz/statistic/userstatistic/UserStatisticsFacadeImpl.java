package pl.stormit.eduquiz.statistic.userstatistic;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class UserStatisticsFacadeImpl implements UserStatisticsFacade {

    private final UserStatisticsRepository userStatisticsRepository;

    @Override
    public Instant lastLoginByUser(UUID userId) {
        UserStatistics userStatistics = userStatisticsRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("The user statistics by id: " + userId + ", does not exist."));
        return userStatistics.getLastLoginDate();
    }
}
