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
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionService;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionMapper;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/questions")
public class QuestionApiController {

    private final QuestionService questionsService;


    private final QuestionMapper questionMapper;

    @GetMapping
    List<Question> getQuestions() {
        return questionsService.getQuestions();
    }

    @GetMapping("{id}")
    Question getQuestion(@PathVariable UUID id) {
        return questionsService.getQuestion(id);
    }

    @PostMapping
    ResponseEntity<QuestionDto> createCategory(@Valid @RequestBody QuestionDto questionRequest) {
        QuestionDto createdQuestion = questionsService.createQuestion(questionRequest);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    ResponseEntity<QuestionDto> updateCategory(@PathVariable UUID id, @RequestBody QuestionDto questionRequest) {
        QuestionDto updateQuestion = questionsService.createQuestion(questionRequest);
        return new ResponseEntity<>(updateQuestion, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    void deleteQuestion(@PathVariable UUID id) {
        questionsService.deleteQuestion(id);
    }
}
