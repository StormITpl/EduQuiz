package pl.stormit.eduquiz.statistic.quizstatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
class QuizStatisticService {

    private final QuizStatisticRepository quizStatisticRepository;
    private final UserRepository userRepository;

    QuizStatistic save(Game game, int score) {

        Optional<User> user = Optional.of(userRepository.findUserByNickname(
                        SecurityContextHolder.getContext().getAuthentication().getName())
                .orElse(userRepository.findUserByNickname("anonim").get()));

        UUID userId = user.get().getId();

        QuizStatistic statistic = new QuizStatistic();

        statistic.setGame(game);
        statistic.setUserId(userId);
        statistic.setScore(score);
        statistic.setDuration(LocalDateTime.now().getLong(ChronoField.SECOND_OF_DAY) - game.getCreatedAt().getLong(ChronoField.SECOND_OF_DAY));

        return quizStatisticRepository.save(statistic);
    }
}