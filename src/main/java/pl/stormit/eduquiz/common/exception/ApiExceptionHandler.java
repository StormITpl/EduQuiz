package pl.stormit.eduquiz.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.stormit.eduquiz.game.contoller.GameController;
import pl.stormit.eduquiz.quizcreator.controllers.*;

@ControllerAdvice(basePackageClasses= {GameController.class, AnswerApiController.class, CategoryApiController.class,
                                        QuestionApiController.class, QuizApiController.class, UserApiController.class})
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<ErrorMessage> handleApiException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage("FAIL", ex), HttpStatus.NOT_FOUND);
    }
}
