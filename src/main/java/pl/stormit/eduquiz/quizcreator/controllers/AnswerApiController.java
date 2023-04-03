package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/questions/{question-id}/answers")
public class AnswerApiController {

    private final AnswerService answerService;

    @GetMapping
    List<Answer> getAnswers(@PathVariable("question-id") UUID questionId) {
        return answerService.getAnswers(questionId);
    }

    @GetMapping("{answer-id}")
    Answer getAnswer(@PathVariable("question-id") UUID questionId, @PathVariable("answer-id") UUID answerId) {
        return answerService.getAnswer(answerId);
    }

    @PostMapping
    ResponseEntity<AnswerDto> createAnswer(@Valid @RequestBody AnswerDto answerRequest) {
        AnswerDto createdAnswer = answerService.createAnswer(answerRequest);
        return new ResponseEntity<>(createdAnswer, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    ResponseEntity<AnswerDto> updateAnswer(@PathVariable UUID id, @RequestBody AnswerDto answerRequest) {
        AnswerDto createdAnswer = answerService.updateAnswer(id, answerRequest);
        return new ResponseEntity<>(createdAnswer, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{answer-id}")
    void deleteAnswer(@PathVariable("answer-id") UUID answerId, @PathVariable("question-id") String parameter) {
        answerService.deleteAnswer(answerId);
    }
}
