package pl.stormit.eduquiz.statistic.quizstatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.statistic.quizstatistic.dto.QuizStatisticDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class QuizStatisticFacadeImp implements QuizStatisticFacade{

    private final QuizService quizService;

    private final QuizStatisticService statisticService;

    @Override
    public List<QuizDto> getThreeNewest() {
        return quizService.getThreeNewest();
    }

    @Override
    public int getLowestScore(UUID id) {return statisticService.getLowestScore(id);}
    public int getHighestScore(UUID id) {return statisticService.getHighestScore(id);}

    @Override
    public QuizStatisticDto addStatisticToDB(Game game, int score) {
        return statisticService.addStatisticToDB(game, score);
    }

}
