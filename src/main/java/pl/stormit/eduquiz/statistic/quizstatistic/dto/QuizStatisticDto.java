package pl.stormit.eduquiz.statistic.quizstatistic.dto;

import pl.stormit.eduquiz.game.domain.entity.Game;

import java.time.LocalDateTime;
import java.util.UUID;

public record QuizStatisticDto(
        UUID id,
        Game game,
        UUID userId,
        int score,
        long duration,
        LocalDateTime createdAt) {
}
