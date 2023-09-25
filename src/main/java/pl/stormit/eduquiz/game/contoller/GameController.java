package pl.stormit.eduquiz.game.contoller;

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
    public ResponseEntity<Object> getGame(@NotNull @PathVariable UUID gameId) {
        GameDto gameDto = gameService.getGame(gameId);

        if (gameDto != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("message", "The Game has been successfully found");
            return new ResponseEntity<>(gameDto, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Game not found");
        }
    }

    @PostMapping("/singleGame")
    public ResponseEntity<Object> createGame(@Valid @RequestBody QuizDto quizRequest) {
        GameDto createGameDto = gameService.createGame(quizRequest);

        if (createGameDto != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("message", "The new game has been successfully created");
            return new ResponseEntity<>(createGameDto, headers, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create the game");
        }
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<Object> playGame(@NotNull @PathVariable UUID gameId, @Valid @RequestBody AnswerDto answerDto) {

        GameDto gameDto = gameService.playGame(gameId, answerDto);

        if (gameDto != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("message", "The game has been successfully started");
            return new ResponseEntity<>(gameDto, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Game not found");
        }
    }

    @PutMapping("/complete/{gameId}")
    public ResponseEntity<Object> completeGame(@NotNull @PathVariable UUID gameId) {

        GameDto gameDto = gameService.completeGame(gameId);

        if (gameDto != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("message", "The game has been successfully completed");
            return new ResponseEntity<>(gameDto, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Game not found");
        }
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Object> deleteGame(@NotNull @PathVariable UUID gameId) {

        boolean deleted = gameService.deleteGame(gameId);

        if (deleted) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("message", "The game has been successfully deleted");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Game not found");
        }
    }
}