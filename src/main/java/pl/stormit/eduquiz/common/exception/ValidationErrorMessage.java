package pl.stormit.eduquiz.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ValidationErrorMessage extends ErrorMessage{

    private Map<String, String> fieldErrors;

    public ValidationErrorMessage(String status, String message, Map<String, String> fieldErrors) {
        super(status, message);
        this.fieldErrors = fieldErrors;
    }
}