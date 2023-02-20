package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizCreationDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizEditingDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/quizzes")

public class QuizApiController {

    private final QuizService quizService;

    @GetMapping
    List<Quiz> getQuizzes() {
        return quizService.getQuizzes();
    }

    @GetMapping("{id}")
    Quiz getQuiz(@PathVariable UUID id) {
        return quizService.getQuiz(id);
    }

    @PostMapping
    ResponseEntity<QuizCreationDto> createQuiz(@Valid @RequestBody QuizCreationDto quizRequest) {
        QuizCreationDto createdQuiz = quizService.createQuiz(quizRequest);
        return new ResponseEntity<>(createdQuiz, HttpStatus.CREATED);
    }

    @PutMapping("{quizId}")
    ResponseEntity<QuizEditingDto> updateQuiz(@PathVariable UUID quizId, @RequestBody QuizEditingDto quizRequest) {
        QuizEditingDto updatedQuiz = quizService.updateQuiz(quizId, quizRequest);
        return new ResponseEntity<>(updatedQuiz, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{quizId}")
    void deleteQuiz(@PathVariable UUID quizId) {
        quizService.deleteQuiz(quizId);
    }
}
