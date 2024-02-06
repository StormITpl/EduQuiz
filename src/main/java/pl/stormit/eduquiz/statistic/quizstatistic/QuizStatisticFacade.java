package pl.stormit.eduquiz.statistic.quizstatistic;

import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;

public interface QuizStatisticFacade {

    List<Quiz> getThreeNewest();

    QuizStatistic add(Game game, int score);
}
