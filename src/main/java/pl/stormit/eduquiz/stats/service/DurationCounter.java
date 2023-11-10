package pl.stormit.eduquiz.stats.service;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationCounter {

    public String getDurationAsString(LocalDateTime start, LocalDateTime finish) {

        Duration duration = Duration.between(start, finish);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public long getDurationAsLong(LocalDateTime start, LocalDateTime finish) {
        return Duration.between(start, finish).getSeconds();
    }
}
