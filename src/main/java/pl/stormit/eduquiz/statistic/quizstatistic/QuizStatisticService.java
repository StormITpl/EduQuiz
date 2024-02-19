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
import java.util.UUID;

@RequiredArgsConstructor
@Service
class QuizStatisticService {

    private final QuizStatisticRepository quizStatisticRepository;
    private final UserRepository userRepository;
    private final QuizStatisticMapper mapper;

    QuizStatisticDto addStatisticToDB(Game game, int score) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID userId = userRepository.findUserByNickname(authentication.getName()).isPresent() ? userRepository.findUserByNickname(authentication.getName()).get().getId() : null;

        QuizStatistic statistic = new QuizStatistic();

        statistic.setGame(game);
        statistic.setUserId(userId);
        statistic.setScore(score);
        statistic.setDuration(LocalDateTime.now().getLong(ChronoField.SECOND_OF_DAY) - game.getCreatedAt().getLong(ChronoField.SECOND_OF_DAY));

        return mapper.mapQuizStatisticEntityToQuizStatisticDto(quizStatisticRepository.save(statistic));
    }

    int getLowestScore(){
        return quizStatisticRepository.findFirstByOrderByScoreAsc();
    }

    int getHighestScore(){
        return quizStatisticRepository.findFirstByOrderByScoreDesc();
    }

}