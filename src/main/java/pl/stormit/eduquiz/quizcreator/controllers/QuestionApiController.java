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
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionService;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestDto;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/questions")
public class QuestionApiController {

    private final QuestionService questionsService;

    @GetMapping
    ResponseEntity<List<QuestionDto>> getQuestions() {

        List<QuestionDto> questionsDtoList = questionsService.getQuestions();
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The list of questions has been successfully found");

        return new ResponseEntity<>(questionsDtoList, headers, HttpStatus.OK);
    }

    @GetMapping("{questionId}")
    ResponseEntity<QuestionDto> getQuestion(@NotNull @PathVariable UUID questionId) {

        QuestionDto questionDto = questionsService.getQuestion(questionId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The question has been successfully found");

        return new ResponseEntity<>(questionDto, headers, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<QuestionDto> createQuestion(@Valid @RequestBody QuestionRequestDto questionRequestDto) {

        QuestionDto createdQuestionDto = questionsService.createQuestion(questionRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The question has been successfully created");

        return new ResponseEntity<>(createdQuestionDto, headers, HttpStatus.CREATED);
    }

    @PutMapping("{questionId}")
    ResponseEntity<QuestionDto> updateQuestion(@NotNull @PathVariable UUID questionId,
                                               @Valid @RequestBody QuestionRequestDto questionRequestDto) {
        QuestionDto updateQuestionDto = questionsService.updateQuestion(questionId, questionRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The question has been successfully updated");

        return new ResponseEntity<>(updateQuestionDto, headers, HttpStatus.OK);
    }

    @DeleteMapping("{questionId}")
    public ResponseEntity<Void> deleteQuestion(@NotNull @PathVariable UUID questionId) {

        questionsService.deleteQuestion(questionId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The question has been successfully deleted");

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
