package pl.stormit.eduquiz.result.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.game.dto.GameIdDto;
import pl.stormit.eduquiz.result.dto.ResultDto;
import pl.stormit.eduquiz.result.service.ResultService;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/results")
public class ResultApiController {

    private final ResultService resultService;

    @PostMapping
    ResponseEntity<ResultDto> createResult(@Valid @RequestBody GameIdDto gameIdDto) {
        if (gameIdDto.id() == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("message", "Invalid input data");
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        ResultDto resultDto = resultService.createResult(gameIdDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The result has been successfully calculated");
        return new ResponseEntity<>(resultDto, headers, HttpStatus.CREATED);
    }

    @GetMapping("{resultId}")
    ResponseEntity<ResultDto> getResult(@NotNull @PathVariable UUID resultId) {
        ResultDto resultDto = resultService.getResult(resultId);

        if (resultDto == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("message", "Result not found");
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The result has been found");
        return new ResponseEntity<>(resultDto, headers, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteResult(@NotNull @PathVariable UUID id) {
        resultService.deleteResult(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
