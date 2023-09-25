package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/quizzes")

public class QuizApiController {

    private final QuizService quizService;

    @GetMapping
    ResponseEntity<List<QuizDto>> getQuizzes() {
        List<QuizDto> quizzesDtoList = quizService.getQuizzes();
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The list of quizzes has been successfully found");
        return new ResponseEntity<>(quizzesDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("{quizId}")
    ResponseEntity<QuizDto> getQuiz(@NotNull @PathVariable UUID quizId) {
        QuizDto foundQuizDto = quizService.getQuiz(quizId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "Quiz has been successfully found");
        return new ResponseEntity<>(foundQuizDto, headers, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<QuizDto> createQuiz(@Valid @RequestBody QuizRequestDto quizRequestDto) {
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "Quiz has been successfully created");
        return new ResponseEntity<>(createdQuiz, headers, HttpStatus.CREATED);
    }

    @PutMapping("{quizId}")
    ResponseEntity<QuizDto> updateQuiz(@NotNull @PathVariable UUID quizId,
                                       @Valid @RequestBody QuizRequestDto quizRequestDto) {
        QuizDto updatedQuiz = quizService.updateQuiz(quizId, quizRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "Quiz has been successfully updated");
        return new ResponseEntity<>(updatedQuiz, headers, HttpStatus.OK);
    }

    @DeleteMapping("{quizId}")
    ResponseEntity<Void> deleteQuiz(@PathVariable UUID quizId) {
        quizService.deleteQuiz(quizId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "Quiz has been successfully deleted");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
