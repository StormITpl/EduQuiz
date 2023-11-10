package pl.stormit.eduquiz.stats.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;

import java.time.Instant;

@AllArgsConstructor
@Service
public class QuizStatsService {

    private final GameRepository gameRepository;

    public Instant getDuration(){
        /** tu musimy zaimplementować pobranie z gameRepository dwóch dat i obliczyć z nich czas trwania
         * return gameRepository.getDurationFromDB;
         * **/

        return Instant.now();
    }
}
