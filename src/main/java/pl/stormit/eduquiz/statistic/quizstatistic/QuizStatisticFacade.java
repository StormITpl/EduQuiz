package pl.stormit.eduquiz.statistic.quizstatistic;

import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;

public interface QuizStatisticFacade {

    List<Quiz> getThreeNewest();
}
