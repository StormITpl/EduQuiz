package pl.stormit.eduquiz.statistic.quizstatistic;

import lombok.RequiredArgsConstructor;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;

import java.util.List;

@RequiredArgsConstructor
public class QuizStatisticFacadeImp implements QuizStatisticFacade{

    private final QuizService quizService;

    @Override
    public List<Quiz> getThreeNewest() {
        return quizService.getThreeNewest();
    }
}
