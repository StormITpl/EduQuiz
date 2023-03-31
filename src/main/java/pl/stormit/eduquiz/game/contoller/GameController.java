package pl.stormit.eduquiz.game.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/games")
@SessionScope
public class GameController {

    private final GameService gameService;

    @PostMapping("/singleGame")
    public ResponseEntity<GameDto> createGame(@Valid @RequestBody QuizDto quizRequest) {

        GameDto createGame = gameService.createGame(quizRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The new game has been successfully created");

        return new ResponseEntity<GameDto>(createGame, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<GameDto> playGame(@PathVariable UUID gameId, @RequestBody AnswerDto answerDto) {

        GameDto game = gameService.playGame(gameId, answerDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The game has been successfully started");

        return new ResponseEntity<>(game, headers, HttpStatus.OK);
    }

    @PutMapping("/complete/{gameId}")
    public ResponseEntity<GameDto> completeGame(@PathVariable UUID gameId){

        GameDto game = gameService.completeGame(gameId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The game has been successfully completed");

        return new ResponseEntity<>(game, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable UUID gameId) {
        gameService.deleteGame(gameId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
