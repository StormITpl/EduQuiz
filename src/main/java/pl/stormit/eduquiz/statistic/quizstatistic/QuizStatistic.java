package pl.stormit.eduquiz.statistic.quizstatistic;

import jakarta.persistence.OneToOne;
import pl.stormit.eduquiz.game.domain.entity.Game;

public class QuizStatistic {

    @OneToOne
    Game game;

    int score;

    long duration;

}
