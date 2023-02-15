package pl.stormit.eduquiz.quizcreator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("{id}")
    Quiz updateQuiz(@PathVariable UUID id, @RequestBody Quiz quiz) {
        return quizService.updateQuiz(id, quiz);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void deleteQuiz(@PathVariable UUID id) {
        quizService.deleteQuiz(id);
    }
}
