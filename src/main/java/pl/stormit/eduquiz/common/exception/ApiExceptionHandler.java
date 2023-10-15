package pl.stormit.eduquiz.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pl.stormit.eduquiz.game.contoller.GameController;
import pl.stormit.eduquiz.quizcreator.controllers.*;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackageClasses= { GameController.class, AnswerApiController.class, CategoryApiController.class,
                                        QuestionApiController.class, QuizApiController.class, UserApiController.class })
public class ApiExceptionHandler {

    @ExceptionHandler(value = { EntityNotFoundException.class })
    protected ResponseEntity<ErrorMessage> handleNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage("FAIL", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class, MethodArgumentTypeMismatchException.class,
            MissingPathVariableException.class })
    protected ResponseEntity<ErrorMessage> handleConstraintViolationException(Exception ex) {
        return new ResponseEntity<>(new ErrorMessage("FAIL", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> notValidFields = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            notValidFields.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(new ValidationErrorMessage("FAIL", "Validation failed", notValidFields), HttpStatus.BAD_REQUEST);
    }
}