package pl.stormit.eduquiz.stats.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;

@AllArgsConstructor
@Service
public class QuizStatsService {

    private final GameRepository gameRepository;

    public String getDuration(Game game){

        DurationCounter durationCounter = new DurationCounter();

        return durationCounter.getDurationAsString(game.getStart(), game.getFinish());
    }
}
