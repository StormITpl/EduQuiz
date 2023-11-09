package pl.stormit.eduquiz.stats.service;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class QuizStatsService {

    public Instant getFinishTimestamp(){
        return Instant.now();
    }
}
