package pl.stormit.eduquiz.stats.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class QuizStatsService {

    private final GameRepository gameRepository;
    private final QuizRepository quizRepository;

    public String getDurationAsString(Game game){

        DurationConverter durationConverter = new DurationConverter();

        return durationConverter.getDurationAsString(game.getStart(), game.getFinish());
    }

    public List<String> getAllQuizzesWithShortestDuration() {

        List<String> durations = new ArrayList<>();

        List<Quiz> quizzes = quizRepository.findAll();

        for (Quiz quiz : quizzes) {

            Optional<Game> game = gameRepository.findTopByQuiz_IdOrderByDurationAsc(quiz.getId());

            game.ifPresent(value -> durations.add(String.format("%s time: %s", quiz.getName(),
                    new DurationConverter().getDurationAsTime(value.getDuration()))));
        }

        return durations;
    }

    public long countAllPlayedGame() {
        return gameRepository.count();
    }

    public List<String> countAllPlayedGameSeparatedByQuiz() {

        List<String> games = new ArrayList<>();

        List<Quiz> quizzes = quizRepository.findAll();

        for (Quiz quiz : quizzes) {

            games.add(String.format("%s time: %s", quiz.getName(),
                    gameRepository.countAllByQuiz_Id(quiz.getId())));
        }

        return games;
    }

    public String countAllPlayedGameForQuiz(UUID quizId) {
        return String.format("%s %s", quizRepository.findById(quizId), gameRepository.countAllByQuiz_Id(quizId));
    }

}
