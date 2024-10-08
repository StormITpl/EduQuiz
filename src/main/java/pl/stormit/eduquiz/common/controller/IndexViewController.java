package pl.stormit.eduquiz.common.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.dto.GameIdDto;
import pl.stormit.eduquiz.game.dto.GameIdMapper;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.domain.repository.ResultRepository;
import pl.stormit.eduquiz.result.dto.ResultDto;
import pl.stormit.eduquiz.result.service.ResultService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class IndexViewController {

    private final CategoryService categoryService;

    private final QuizService quizService;

    private final GameService gameService;
    private final GameRepository gameRepository;
    private final GameIdMapper gameIdMapper;

    private final AnswerService answerService;

    private final ResultService resultService;
    private final ResultRepository resultRepository;

    @GetMapping
    public String indexView(Model model) {

        model.addAttribute("categories", categoryService.getCategories());
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

        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("quizzes", quizService.getQuizzesByCategoryId(id));

        return "showQuizzesByCategory";
    }

    @GetMapping("/quiz/{id}/{questionIndex}")
    public String quiz(@PathVariable UUID id,
                       @PathVariable int questionIndex,
                       Model model, HttpSession httpSession) {

        QuizDto quizDto = quizService.getQuiz(id);
        GameDto gameDto = gameService.createGame(quizDto);
        UUID gameDtoUUID = gameDto.id();
        Queue<Question> questionQueue = new LinkedList<>(quizDto.questions());

        addAttributes(questionIndex, model, httpSession, questionQueue, quizDto, gameDtoUUID);

        return "quiz";
    }

    @PostMapping("/quiz/{id}/{questionIndex}")
    public String nextQuestion(@PathVariable("id") UUID id,
                               @PathVariable("questionIndex") int questionIndex,
                               @RequestParam("radio") String selectedAnswerIdRequest,
                               @RequestParam("gameDtoId") String gameDtoId,
                               Model model, HttpSession httpSession) {

        QuizDto quizDto = quizService.getQuiz(id);
        UUID selectedAnswerId = UUID.fromString(selectedAnswerIdRequest);
        UUID gameDtoUUID = UUID.fromString(gameDtoId);
        gameService.playGame(gameDtoUUID, answerService.getAnswer(selectedAnswerId));

        Queue<Question> questionQueue = (Queue<Question>) httpSession.getAttribute("questionQueue");

        if (questionQueue.isEmpty()) {
            model.addAttribute("gameDtoId", gameDtoUUID);
            gameService.completeGame(gameDtoUUID);
            return "confirmAnswers";
        }

        addAttributes(questionIndex, model, httpSession, questionQueue, quizDto, gameDtoUUID);

        return "quiz";
    }

    private void addAttributes(int questionIndex, Model model, HttpSession httpSession, Queue<Question> questionQueue, QuizDto quizDto, UUID gameDtoUUID) {
        Question currentQuestion = questionQueue.poll();

        List<Answer> currentAnswers = currentQuestion.getAnswers();

        model.addAttribute("quiz", quizDto);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("answers", currentAnswers);
        model.addAttribute("questionIndex", ++questionIndex);
        model.addAttribute("gameDtoId", gameDtoUUID);
        httpSession.setAttribute("questionQueue", questionQueue);
    }

    @GetMapping("/quiz/{gameDtoId}/completeResults")
    public String completeResults(@PathVariable("gameDtoId") String id, Model model) {

        UUID gameId = UUID.fromString(id);

        Optional<Game> game = gameRepository.findById(gameId);
        Game gameTest = game.get();
        GameIdDto gameIdDto = gameIdMapper.mapGameEntityToGameIdDto(gameTest);

        ResultDto resultDto = resultService.createResult(gameIdDto);
        Optional<Result> resultById = resultRepository.findById(resultDto.id());
        System.out.println(resultById.get().getScore());
        List<Question> questionList = gameTest.getQuiz().getQuestions();

        model.addAttribute("results", resultById.get());
        model.addAttribute("questionList", questionList);

        return "results";
    }
}
