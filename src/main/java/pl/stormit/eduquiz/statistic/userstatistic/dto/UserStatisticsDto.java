package pl.stormit.eduquiz.statistic.userstatistic.dto;

import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.time.Instant;
import java.util.UUID;

public record UserStatisticsDto(
        UUID id,
        User user,
        Instant lastLogin,
        int loginCount,
        int createdQuizzesCount,
        int solvedQuizCount) {
}
