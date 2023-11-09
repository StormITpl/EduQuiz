package pl.stormit.eduquiz.result.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.game.dto.GameIdDto;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.dto.ResultDto;
import pl.stormit.eduquiz.result.service.ResultService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/results")
public class ResultApiController {

    private final ResultService resultService;

    @PostMapping
    ResponseEntity<ResultDto> createResult(@Valid @RequestBody GameIdDto gameIdDto) {
        ResultDto resultDto = resultService.createResult(gameIdDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The result has been successfully calculated");
        return new ResponseEntity<ResultDto>(resultDto, headers, HttpStatus.CREATED);
    }

    @GetMapping("{resultId}")
    ResponseEntity<ResultDto> getResult(@NotNull @PathVariable UUID resultId) {
        ResultDto resultDto = resultService.getResult(resultId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The result has been found");
        return new ResponseEntity<ResultDto>(resultDto, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteResult(@NotNull @PathVariable UUID id) {
        resultService.deleteResult(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PostMapping("/quiz")
//    public String submitQuiz(Model model, @RequestParam("answers") List<String> answers){
//
//        Date endTime = new Date();
//        Date startTime = (Date) model.addAttribute("startTime");
//        long duration = endTime.getTime() - startTime.getTime();
//
//        Result result = new Result();
//        result.setQuizDuration(duration);
//
//        model.addAttribute("result", result);
//        return "result";
//    }
}
