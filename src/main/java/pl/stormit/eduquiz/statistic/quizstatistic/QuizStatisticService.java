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

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UUID userId = userRepository.findUserByNickname(authentication.getName()).get().getId();
            statistic.setUserId(userId);
            quizStatisticRepository.save(statistic);
        }

        return mapper.mapQuizStatisticEntityToQuizStatisticDto(statistic);
    }

    int getLowestScore(){
        return quizStatisticRepository.findLastQuizStatisticByScore();
    }

    int getHighestScore(){
        return quizStatisticRepository.findTopQuizStatisticByScore();
    }

    public Map<String, Long> getDurationForEachQuizzes(boolean best) {
        Map<String, Long> map = new LinkedHashMap<>();

        if (best){
            quizStatisticRepository.findAll().stream()
                    .sorted(Comparator.comparing(QuizStatistic::getDuration).reversed())
                    .forEach(quizStatistic -> {
                        map.put(quizStatistic.getGame().getQuiz().getName(), quizStatistic.getDuration());
                    });
        } else {
            quizStatisticRepository.findAll().stream()
                    .sorted(Comparator.comparing(QuizStatistic::getDuration))
                    .forEach(quizStatistic -> {
                        map.put(quizStatistic.getGame().getQuiz().getName(), quizStatistic.getDuration());
                    });
        }
        return map;
    }
}