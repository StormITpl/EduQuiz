package pl.stormit.eduquiz.stats.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationConverterTest {

    long seconds;
    LocalDateTime start;
    LocalDateTime finish;

    @BeforeEach
    void setUp() {
        seconds = 62;
        start = LocalDateTime.now();
        finish = start.plusSeconds(seconds);
    }

    @Test
    void shouldReturnCorrectPartTime() {

        DurationConverter durationConverter = new DurationConverter();
        durationConverter.getDurationAsString(start, finish);

        assertEquals(0, durationConverter.getHours());
        assertEquals(1, durationConverter.getMinutes());
        assertEquals(2, durationConverter.getSeconds());
    }

    @Test
    void shouldReturnCorrectDurationAsString() {
        DurationConverter durationConverter = new DurationConverter();

        assertEquals("00:01:02", durationConverter.getDurationAsString(start, finish));
    }

    @Test
    void shouldReturnCorrectDurationAsLong() {
        DurationConverter durationConverter = new DurationConverter();

        assertEquals(seconds, durationConverter.getDurationAsLong(start, finish));
    }

    @Test
    void shouldReturnCorrectDurationAsTime() {
        DurationConverter durationConverter = new DurationConverter();

        assertEquals(LocalTime.of(0, 1, 2), durationConverter.getDurationAsTime(seconds));
    }


}