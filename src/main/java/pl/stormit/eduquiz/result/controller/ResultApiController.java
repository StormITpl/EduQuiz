package pl.stormit.eduquiz.result.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.service.ResultService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/results")
public class ResultApiController {

    private final ResultService resultService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Result createResult(@RequestBody Game game, @RequestBody Quiz quiz) {
        return resultService.createResult(game, quiz);
    }

    @GetMapping("{id}")
    Result getResult(@PathVariable UUID id){
        return resultService.getResult(id);
    }

}
