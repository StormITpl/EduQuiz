package pl.stormit.eduquiz.statistic.quizstatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.time.temporal.ChronoField;
import java.util.UUID;

@RequiredArgsConstructor
@Service
class QuizStatisticService {

    private final QuizStatisticRepository quizStatisticRepository;

    QuizStatistic add(Game game, int score, long duration) {
        QuizStatistic statistic = quizStatisticRepository.findByGame_Id(game.getId());

        if(statistic == null) {
            return save(game, score, duration);
        }

        return update(statistic, score, duration);
    }

    private QuizStatistic update(QuizStatistic statistic, int score, long duration) {
        statistic.setScore(statistic.getScore() + score);
        statistic.setDuration(duration - statistic.getCreatedAt().getLong(ChronoField.NANO_OF_SECOND));
        return quizStatisticRepository.save(statistic);
    }

    private QuizStatistic save(Game game, int score, long duration) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        UUID userId = user == null ? null : user.getId();

        QuizStatistic statistic = new QuizStatistic();

        statistic.setGame(game);
        statistic.setUserId(userId);
        statistic.setScore(score);
        statistic.setDuration(duration);

        return quizStatisticRepository.save(statistic);
    }
}