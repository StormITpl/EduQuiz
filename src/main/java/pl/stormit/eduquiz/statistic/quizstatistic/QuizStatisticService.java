package pl.stormit.eduquiz.statistic.quizstatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.UUID;

@RequiredArgsConstructor
@Service
class QuizStatisticService {

    private final QuizStatisticRepository quizStatisticRepository;

    QuizStatistic save(Game game, int score) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        UUID userId = user == null ? null : user.getId();

        QuizStatistic statistic = new QuizStatistic();

        statistic.setGame(game);
        statistic.setUserId(userId);
        statistic.setScore(score);
        statistic.setDuration(game.getCreatedAt().getLong(ChronoField.NANO_OF_SECOND) - LocalDateTime.now().getLong(ChronoField.NANO_OF_SECOND));

        return quizStatisticRepository.save(statistic);
    }
}