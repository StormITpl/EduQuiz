package pl.stormit.eduquiz.game.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import pl.stormit.eduquiz.quizcreator.quiz.dto.QuizDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/games")
public class GameController {

    private final GameService gameService;

    //Wybieramy quiz <-> tworzymy encje Game -> przypisujemy konkretny quiz do Game

    @PostMapping
    public ResponseEntity<GameDto> createGame(@Valid @RequestBody QuizDto quizRequest) {

        GameDto createGame = gameService.createGame(quizRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", "The new game has been successfully created");

        return new ResponseEntity<>(createGame, headers, HttpStatus.CREATED);
    }

    @GetMapping("/quiz/{quiz-id}")
    public List<Question> findAllQuizQuestions(@PathVariable("quiz-id") UUID id) {
        return gameService.findAllQuizQuestions(id);
    }

    @GetMapping("/quiz/questions/{question-id}")
    public List<Answer> findAllAnswersForQuestion(@PathVariable("question-id") UUID id) {
        return gameService.findAllAnswersForQuestion(id);
    }

    @PostMapping("/quiz/questions/{question-id}/answers")
    public List<Answer> chosenAnswer(@PathVariable("question-id") UUID id, @RequestBody Answer answerRequest) {
        return gameService.addUserAnswer(answerRequest);
    }
}
