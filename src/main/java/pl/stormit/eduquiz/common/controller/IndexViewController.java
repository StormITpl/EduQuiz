package pl.stormit.eduquiz.common.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;

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

    @GetMapping("quiz/{id}")
    public String quiz(@PathVariable UUID id, Model model){

        model.addAttribute("quiz", quizService.getQuiz(id));
        model.addAttribute("question", quizService.getQuiz(id).questions());

        return "quiz";
    }
}
