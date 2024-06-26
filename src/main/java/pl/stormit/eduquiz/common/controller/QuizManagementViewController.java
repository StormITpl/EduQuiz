package pl.stormit.eduquiz.common.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

@Controller
@RequestMapping("/quizManagement")
@AllArgsConstructor
public class QuizManagementViewController {

    private final QuizService quizService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @GetMapping
    public String quizManagementView(Model model, HttpSession httpSession) {
        List<CategoryDto> categories = categoryService.getCategories();

        Queue<Question> questions = new LinkedList<>();
        httpSession.setAttribute("questionQueue", questions);

        model.addAttribute("categories", categories);

        model.addAttribute("questin", new Question());

        return "quizManagement";
    }

    @PostMapping("addQuestion")
    public String addQuestion(Model model,
                              @RequestParam("quizName") String quizName,
                              @RequestParam("categoryId") Category category,
                              @RequestParam("questionContent") String questionContent,
                              @RequestParam("answer1Content") String answer1Content,
                              @RequestParam(value = "correctAnswer", required = false, defaultValue = "0") int correctAnswer,
                              @RequestParam("answer2Content") String answer2Content,
                              @RequestParam("answer3Content") String answer3Content,
                              @RequestParam("answer4Content") String answer4Content,
                              HttpSession httpSession) {

        Question question = new Question();
        question.setContent(questionContent);

        Answer answer1 = new Answer();
        answer1.setContent(answer1Content);

        Answer answer2 = new Answer();
        answer2.setContent(answer2Content);

        Answer answer3 = new Answer();
        answer3.setContent(answer3Content);

        Answer answer4 = new Answer();
        answer4.setContent(answer4Content);

        switch (correctAnswer) {
            case 0:
                model.addAttribute("message", "Wybierz, która odpowiedź jest poprawna");
                model.addAttribute("questionContent", questionContent);
                model.addAttribute("answer1Content", answer1Content);
                model.addAttribute("answer2Content", answer2Content);
                model.addAttribute("answer3Content", answer3Content);
                model.addAttribute("answer4Content", answer4Content);
                model.addAttribute("categories", category);
                model.addAttribute("categoryId", category);
                model.addAttribute("quizName", quizName);
                return "quizManagement";
            case 1:
                answer1.setCorrect(true);
                break;
            case 2:
                answer2.setCorrect(true);
                break;
            case 3:
                answer3.setCorrect(true);
                break;
            case 4:
                answer4.setCorrect(true);
                break;
        }

        Queue<Question> questions = (Queue<Question>) httpSession.getAttribute("questionQueue");

        question.setAnswers(List.of(answer1, answer2, answer3, answer4));
        question.getAnswers().forEach(answer -> answer.setQuestion(question));
        questions.add(question);

        httpSession.setAttribute("questionQueue", questions);
        model.addAttribute("categories", category);
        model.addAttribute("categoryId", category);
        model.addAttribute("quizName", quizName);

        return "quizManagement";
    }

    @PostMapping("/createQuiz")
    public String createQuiz(@RequestParam("quizName") String quizName,
                             @ModelAttribute("categoryId") Category category,
                             HttpSession httpSession) {

        List<Question> questions = (List<Question>) httpSession.getAttribute("questionQueue");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByNickname(authentication.getName()).orElse(null);

        QuizRequestDto quizRequest = new QuizRequestDto(quizName, category, user, questions);

        quizService.createQuiz(quizRequest);

        return "redirect:/quizManagement";
    }

    @PostMapping("/deleteQuiz/{quizId}")
    public String deleteQuiz(@PathVariable("quizId") UUID quizId) {
        quizService.deleteQuiz(quizId);
        return "redirect:/quizManagement";
    }
}
