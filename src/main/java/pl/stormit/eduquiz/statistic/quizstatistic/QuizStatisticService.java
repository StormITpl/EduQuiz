package pl.stormit.eduquiz.statistic.quizstatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;
import pl.stormit.eduquiz.statistic.quizstatistic.dto.QuizStatisticDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
class QuizStatisticService {

    private final QuizStatisticRepository quizStatisticRepository;
    private final UserRepository userRepository;
    private final QuizStatisticMapper mapper;

    QuizStatisticDto addStatisticToDB(Game game, int score) {

        QuizStatistic statistic = new QuizStatistic();
        statistic.setGame(game);
        statistic.setScore(score);
        statistic.setDuration(LocalDateTime.now().getLong(ChronoField.SECOND_OF_DAY) - game.getCreatedAt().getLong(ChronoField.SECOND_OF_DAY));

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UUID userId = userRepository.findUserByNickname(authentication.getName()).get().getId();
            statistic.setUserId(userId);
            quizStatisticRepository.save(statistic);
        }

        return mapper.mapQuizStatisticEntityToQuizStatisticDto(statistic);
    }

    int getLowestScore() {
        return quizStatisticRepository.findLastQuizStatisticByScore();
    }

    int getHighestScore() {
        return quizStatisticRepository.findTopQuizStatisticByScore();
    }

    public Map<String, Long> getDurationForEachQuiz(boolean best) {
        Map<String, Long> map = new LinkedHashMap<>();

        quizStatisticRepository.findAll().stream()
                .sorted(best ?
                        Comparator.comparing(QuizStatistic::getDuration) :
                        Comparator.comparing(QuizStatistic::getDuration).reversed())
                .forEach(quizStatistic ->
                        map.putIfAbsent(quizStatistic.getGame().getQuiz().getName(), quizStatistic.getDuration()));
        return map;
    }

    public Map<String, Long> getPopularQuizInLastSevenDays() {
        Map<String, Long> map = new LinkedHashMap<>();
        LocalDateTime sevenDays = LocalDateTime.now().minusDays(7);

        quizStatisticRepository.findDistinctByCreatedAtAfterOrderByGame_Quiz_NameAsc(sevenDays)
                .forEach(quizStatistic -> {
                    map.merge(quizStatistic.getGame().getQuiz().getName(), 1L, Long::sum);
                });

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}