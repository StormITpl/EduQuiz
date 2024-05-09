package pl.stormit.eduquiz.statistic.quizstatistic;

import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.statistic.quizstatistic.dto.QuizStatisticDto;

import java.util.List;
import java.util.Map;

public interface QuizStatisticFacade {

    List<QuizDto> getThreeNewest();

    int getLowestScore();

    int getHighestScore();

    QuizStatisticDto addStatisticToDB(Game game, int score);

    Map<String, Long> getDurationForEachQuiz(boolean best);
}
