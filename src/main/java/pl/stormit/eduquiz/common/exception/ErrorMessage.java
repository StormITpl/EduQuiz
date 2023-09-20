package pl.stormit.eduquiz.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorMessage {

    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    public ErrorMessage(String status, Exception ex) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = ex.getMessage();
    }

}
