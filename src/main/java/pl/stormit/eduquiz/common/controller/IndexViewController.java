package pl.stormit.eduquiz.common.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionService;
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

    private final QuestionService questionService;

    private final AnswerService answerService;

    @GetMapping
    public String indexView(Model model){

        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("quizzes", quizService.getQuizzes());

        return "index";
    }

    @GetMapping("/quizzes")
    public String showQuizzes(Model model){

        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("quizzes", quizService.getQuizzes());

        return "showQuizzes";
    }


    @GetMapping("/category/{id}")
    public String viewQuiz(@PathVariable UUID id, Model model){

        model.addAttribute("quizzes", quizService.getQuizzes());

        return "index";
    }

    @GetMapping("/quiz/{id}/{questionIndex}")
    public String quiz(@PathVariable UUID id, @PathVariable int questionIndex, Model model){

        QuizDto quiz = quizService.getQuiz(id);
        List<Question> questions = quiz.questions();

//        questionIndex = (Integer) model.getAttribute("questionIndex");

//        if (questionIndex == null) {
//            questionIndex = 0;
//        }


        Question currentQuestion = questions.get(questionIndex);
        List<Answer> currentAnswers = currentQuestion.getAnswers();

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("answers", currentAnswers);
        model.addAttribute("questionIndex", questionIndex);

        return "quiz";
    }

    @PostMapping("/quiz/{id}/{questionIndex}")
    public String nextQuestion(@PathVariable("id") UUID id, @PathVariable("questionIndex") int questionIndex, Model model) {
        QuizDto quiz = quizService.getQuiz(id);
        List<Question> questions = quiz.questions();

            questionIndex++;

        Question currentQuestion = questions.get(questionIndex);
        List<Answer> currentAnswers = currentQuestion.getAnswers();

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("answers", currentAnswers);
        model.addAttribute("questionIndex", questionIndex);

//        questionIndex++;
//        String newUrl = "quiz/{id}/questionIndex?questionIndex=" + questionIndex;

//        return "quiz";
//        return "redirect:" +newUrl;
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println(questionIndex);
//        return "redirect:/quiz/{id}/{questionIndex}";
        return "quiz";
    }
//    @GetMapping("/quiz/{id}/{questionIndex}")
//    public String nextQuestionGet(@PathVariable UUID id, @PathVariable Integer questionIndex, Model model) {
//        QuizDto quiz = quizService.getQuiz(id);
//        List<Question> questions = quiz.questions();
//
//        if (questionIndex + 1 < questions.size()) {
//            questionIndex++;
//        }
//
//        Question currentQuestion = questions.get(questionIndex);
//        List<Answer> currentAnswers = currentQuestion.getAnswers();
//
//        model.addAttribute("quiz", quiz);
//        model.addAttribute("question", currentQuestion);
//        model.addAttribute("answers", currentAnswers);
//        model.addAttribute("questionIndex", questionIndex);
//
//        return "quiz";
////        questionIndex++;
////        String newUrl = "quiz/{id}/questionIndex?questionIndex=" + questionIndex;
////        return "redirect:" +newUrl;
////        return "redirect:/quiz/{id}/{questionIndex}";
//    }
}
