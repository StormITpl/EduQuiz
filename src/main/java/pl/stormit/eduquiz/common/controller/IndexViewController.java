package pl.stormit.eduquiz.common.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class IndexViewController {

    private final CategoryService categoryService;

    private final QuizService quizService;

    private final GameService gameService;

    private final AnswerService answerService;

    @GetMapping
    public String indexView(Model model) {

        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("quizzes", quizService.getQuizzes());

        return "index";
    }

    @GetMapping("/quizzes")
    public String showQuizzes(Model model) {

        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("quizzes", quizService.getQuizzes());

        return "showQuizzes";
    }


    @GetMapping("/category/{id}")
    public String viewQuiz(@PathVariable UUID id, Model model) {

        model.addAttribute("quizzes", quizService.getQuizzes());

        return "index";
    }

    @GetMapping("/quiz/{id}/{questionIndex}")
    public String quiz(@PathVariable UUID id, @PathVariable int questionIndex, Model model) {

        QuizDto quizDto = quizService.getQuiz(id);
        List<Question> questions = quizDto.questions();
        GameDto gameDto = gameService.createGame(quizDto);

        Question currentQuestion = questions.get(questionIndex);
        List<Answer> currentAnswers = currentQuestion.getAnswers();

        model.addAttribute("quiz", quizDto);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("answers", currentAnswers);
        model.addAttribute("questionIndex", questionIndex);
        model.addAttribute("gameDtoId", gameDto.id());

        return "quiz";
    }

    @PostMapping("/quiz/{id}/{questionIndex}")
    public String nextQuestion(@PathVariable("id") UUID id,
                               @PathVariable("questionIndex") int questionIndex,
                               @RequestParam("radio") String selectedAnswerIdRequest,
                               @RequestParam("gameDtoId") String gameDtoId,
                               Model model) {
        QuizDto quiz = quizService.getQuiz(id);
        List<Question> questions = quiz.questions();
        UUID selectedAnswerId = UUID.fromString(selectedAnswerIdRequest);
        UUID gameDtoUUID = UUID.fromString(gameDtoId);
        gameService.playGame(gameDtoUUID, answerService.getAnswer(selectedAnswerId));

        questionIndex++;

        Question currentQuestion = questions.get(questionIndex);
        List<Answer> currentAnswers = currentQuestion.getAnswers();

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("answers", currentAnswers);
        model.addAttribute("questionIndex", questionIndex);
        model.addAttribute("gameDtoId", gameDtoId);

        return "quiz";
    }
}

