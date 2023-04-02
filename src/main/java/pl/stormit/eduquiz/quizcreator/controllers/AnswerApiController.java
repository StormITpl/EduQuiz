package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/questions/{question-id}/answers")
public class AnswerApiController {

    private final AnswerService answerService;

    @GetMapping
    ResponseEntity<List<AnswerDto>> getAnswers(@NotNull @PathVariable("question-id") UUID questionId) {

        List<AnswerDto> answersDtoList = answerService.getAnswers(questionId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The list of answers has been successfully found");

        return new ResponseEntity<>(answersDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("{answer-id}")
    ResponseEntity<AnswerDto> getAnswer(@NotNull @PathVariable("question-id") UUID questionId,
                                        @NotNull @PathVariable("answer-id") UUID answerId) {

        AnswerDto answerDto = answerService.getAnswer(answerId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The answer has been successfully found");

        return new ResponseEntity<>(answerDto, headers, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<AnswerDto> createAnswer(@NotNull @PathVariable("question-id") UUID questionId,
                                           @Valid @RequestBody AnswerDto answerRequest) {

        AnswerDto createdAnswer = answerService.createAnswer(questionId, answerRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The answer has been successfully created");

        return new ResponseEntity<>(createdAnswer, headers, HttpStatus.CREATED);
    }

    @PutMapping("{answer-id}")
    ResponseEntity<AnswerDto> updateAnswer(@NotNull @PathVariable("question-id") UUID questionId,
                                           @NotNull @PathVariable("answer-id") UUID answerId,
                                           @Valid @RequestBody AnswerDto answerRequest) {

        AnswerDto updateAnswer = answerService.updateAnswer(answerId, answerRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The answer has been successfully updated");

        return new ResponseEntity<>(updateAnswer, headers, HttpStatus.OK);
    }

    @DeleteMapping("{answer-id}")
    public ResponseEntity<Void> deleteAnswer(@NotNull @PathVariable("question-id") UUID questionId,
                                             @NotNull @PathVariable("answer-id") UUID answerId) {
        answerService.deleteAnswer(answerId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The answer has been successfully deleted");

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
