package pl.stormit.eduquiz.stats.service;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class DurationConverter {

    private int hours;
    private int minutes;
    private int seconds;

    public String getDurationAsString(LocalDateTime start, LocalDateTime finish) {

        setTimePart(Duration.between(start, finish));
        return getDurationAsTime().toString();
    }

    public long getDurationAsLong(LocalDateTime start, LocalDateTime finish) {
        return Duration.between(start, finish).getSeconds();
    }

    private void setTimePart(Duration duration) {
        hours = (int) duration.toHours();
        minutes = (int) duration.toMinutes() % 60;
        seconds = (int) duration.getSeconds() % 60;;
    }

    public LocalTime getDurationAsTime(long seconds) {
        setTimePart(Duration.ofSeconds(seconds));
        return getDurationAsTime();
    }

    private LocalTime getDurationAsTime() {
        return LocalTime.of(hours, minutes, seconds);
    }
}
