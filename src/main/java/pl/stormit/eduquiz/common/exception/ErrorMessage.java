package pl.stormit.eduquiz.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ErrorMessage {

    private String status;

    private String timestamp;

    private String message;

    public ErrorMessage(String status, String message) {
        this.status = status;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.message = message;
    }
}