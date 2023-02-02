package pl.stormit.eduquiz.game.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.category.domain.model.Category;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/games")
public class GameController{

    private final GameService gameService;

    //Sport(id1) / Geografia(id2)
    // Sport - quiz1(id1),quiz2(id2),quiz3(id3)
    // Geografia - quiz1(id1),quiz2(id2),quiz3(id3)


    @PostMapping
    Quiz chosenQuiz(@RequestBody Quiz quiz){
        return gameService.chooseQuiz(quiz);
    }

    @GetMapping("/quiz/{quiz-id}")
    public List<Question> findAllQuizQuestions(@PathVariable("quiz-id") UUID id){
        return gameService.findAllQuizQuestions(id);
    }

    @GetMapping("/quiz/questions/{question-id}")
    public List<Answer> findAllAnswersForQuestion(@PathVariable("question-id") UUID id){
        return gameService.findAllAnswersForQuestion(id);
    }

    @PostMapping("/quiz/questions/{question-id}/answers")
    public List<Answer> chosenAnswer(@PathVariable("question-id") UUID id, @RequestBody Answer answerRequest){

        return gameService.addUserAnswer(answerRequest);
    }
}
