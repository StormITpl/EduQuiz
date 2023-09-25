package pl.stormit.eduquiz.quizcreator.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerRequestDto;

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
                                           @Valid @RequestBody AnswerRequestDto answerRequestDto) {

        AnswerDto createdAnswerDto = answerService.createAnswer(questionId, answerRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The answer has been successfully created");

        return new ResponseEntity<>(createdAnswerDto, headers, HttpStatus.CREATED);
    }

    @PutMapping("{answer-id}")
    ResponseEntity<AnswerDto> updateAnswer(@NotNull @PathVariable("question-id") UUID questionId,
                                           @NotNull @PathVariable("answer-id") UUID answerId,
                                           @Valid @RequestBody AnswerRequestDto answerRequestDto) {

        AnswerDto updateAnswerDto = answerService.updateAnswer(answerId, answerRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The answer has been successfully updated");

        return new ResponseEntity<>(updateAnswerDto, headers, HttpStatus.OK);
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
