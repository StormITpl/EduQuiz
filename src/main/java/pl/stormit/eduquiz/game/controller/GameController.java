package pl.stormit.eduquiz.game.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.context.annotation.SessionScope;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/games")
@SessionScope
public class GameController {

    private final GameService gameService;

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getGame(@NotNull @PathVariable UUID gameId) {

        GameDto gameDto = gameService.getGame(gameId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The Game has been successfully found");

        return new ResponseEntity<>(gameDto, headers, HttpStatus.OK);
    }

    @PostMapping("/singleGame")
    public ResponseEntity<GameDto> createGame(@Valid @RequestBody QuizDto quizRequest) {

        GameDto createGameDto = gameService.createGame(quizRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The new game has been successfully created");

        return new ResponseEntity<>(createGameDto, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<GameDto> playGame(@NotNull @PathVariable UUID gameId,
                                            @Valid @RequestBody AnswerDto answerDto) {

        GameDto gameDto = gameService.playGame(gameId, answerDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The game has been successfully started");

        return new ResponseEntity<>(gameDto, headers, HttpStatus.OK);
    }

    @PutMapping("/complete/{gameId}")
    public ResponseEntity<GameDto> completeGame(@NotNull @PathVariable UUID gameId) {

        GameDto gameDto = gameService.completeGame(gameId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The game has been successfully completed");

        return new ResponseEntity<>(gameDto, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@NotNull @PathVariable UUID gameId) {

        gameService.deleteGame(gameId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The game has been successfully deleted");

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
