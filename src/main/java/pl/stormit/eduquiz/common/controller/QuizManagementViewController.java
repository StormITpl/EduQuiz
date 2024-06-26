package pl.stormit.eduquiz.common.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/quizManagement")
@AllArgsConstructor
public class QuizManagementViewController {

    private final QuizService quizService;
    private final CategoryService categoryService;

    @GetMapping
    public String quizManagementView(Model model) {
        List<CategoryDto> categories = categoryService.getCategories();

        model.addAttribute("categories", categories);

        return "quizManagement";
    }

    @PostMapping("addQuestion")
    public String addQuestion(Model model,
                              @RequestParam("quizName") String quizName,
                              @RequestParam("categorySelected") Category category,
                              RedirectAttributes ra) {
        ra.addFlashAttribute("quizName", quizName);
        ra.addFlashAttribute("categorySelected", category);

        return "redirect:/quizManagement";
    }

    @PostMapping("/createQuiz")
    public String createQuiz(@RequestParam("quizName") String quizName,
                             @RequestParam("categoryId") Category category,
                             @RequestParam("questions") List questions) {

        QuizRequestDto quizRequest = new QuizRequestDto(quizName, category, null, questions);

        quizService.createQuiz(quizRequest);

        return "redirect:/quizManagement";
    }

    @PostMapping("/deleteQuiz/{quizId}")
    public String deleteQuiz(@PathVariable("quizId") UUID quizId) {
        quizService.deleteQuiz(quizId);
        return "redirect:/quizManagement";
    }
}
