package pl.stormit.eduquiz.admin.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.category.CategoryService;
import pl.stormit.eduquiz.quizcreator.domain.category.dto.CategoryDto;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionService;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.user.Status;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pl.stormit.eduquiz.common.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/admin/quizzes")
@RequiredArgsConstructor
public class QuizAdminViewController {

    private final QuizService quizService;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @GetMapping
    public String indexView(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);

        String reverseSort;

        if ("asc".equals(direction)) {
            reverseSort = "desc";
        } else {
            reverseSort = "asc";
        }

        Page<Quiz> quizzesPage = quizService.getQuizzes(search, pageable);
        model.addAttribute("quizzesPage", quizzesPage);
        model.addAttribute("search", search);
        model.addAttribute("reverseSort", reverseSort);

        paging(model, quizzesPage);

        return "admin/quiz/index";
    }

    @GetMapping("add")
    public String addView(Model model) {

        List<Question> questions = new ArrayList<>();

        for (int i = 0; i < 15; i++)
        {
            questions.add(new Question());
            List<Answer> answers=new ArrayList<>();

            for (int j = 0; j < 4; j++)
            {
                answers.add(new Answer(null, null, null));
            }
            questions.get(i).setAnswers(answers);
        }

        model.addAttribute("quiz", new Quiz(null, null, null, null, questions,
                null, null, null));

        List<CategoryDto> categoryDto = categoryService.getCategories();
        model.addAttribute("categoryList", categoryDto);

        return "admin/quiz/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("quiz") Quiz quiz,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttributes) {

        for (int i = quiz.getQuestions().size()-1; i >= 0; i--)
        {
            if (!(quiz.getQuestions().get(i).getContent().isEmpty()))
            {
                if (quiz.getQuestions().get(i).getCorrectAnswer()==null)
                {
                    model.addAttribute("emptyAnswer", "Question number " + (i + 1) + " does not have a correct answer marked");

                    return "admin/quiz/add";
                }
            }
        }

        if (quizService.checkIfQuizNameAvailable(quiz.getName())) {

            if (bindingResult.hasErrors()) {

                return "admin/quiz/add";
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User test = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            User user = userService.getUser(test.getUsername());
            user.setQuizzes(null);

            CategoryDto category_local = categoryService.getCategory(quiz.getCategory().getName());

            if (category_local.id() == null)
            {
                category_local = categoryService.createCategory(category_local);
            }

            for (int i = quiz.getQuestions().size()-1; i > 0; i--)
            {
                if (quiz.getQuestions().get(i).getContent().isEmpty())
                {
                    quiz.getQuestions().remove(i);
                }
            }

            QuizRequestDto quizRequestDto;

            if (user.getRole().name().equals("ROLE_ADMIN"))
            {
                 quizRequestDto = new QuizRequestDto(quiz.getName(), new Category(category_local.id(), category_local.name(),
                         null), user, quiz.getQuestions(), Status.VERIFIED, quiz.getCreatedAt(), quiz.getGames());
            }
            else
            {
                 quizRequestDto = new QuizRequestDto(quiz.getName(), new Category(category_local.id(), category_local.name(),
                         null), user, quiz.getQuestions(), Status.UNVERIFIED, quiz.getCreatedAt(), quiz.getGames());
            }

            QuizDto quizDto = quizService.createQuiz(quizRequestDto);

            for (int i = 0; i < quiz.getQuestions().size(); i++)
            {
                QuestionDto question = questionService.createQuestion(new QuestionRequestDto(quizDto.questions().get(i).getContent(),
                        quizDto.questions().get(i).getCorrectAnswer(), new Quiz(quizDto.id(),quizDto.name(), quizDto.category(),
                        quizDto.user(), quizDto.questions(), quizDto.games(), quizDto.status(), quizDto.createdAt()),
                        quizDto.questions().get(i).getAnswers()));

                for (int j = 0; j < quiz.getQuestions().get(i).getAnswers().size(); j++)
                {
                    if (String.valueOf(j).equals(quiz.getQuestions().get(i).getCorrectAnswer()))
                    {
                        AnswerDto answerDto = answerService.createAnswer(question.id(), new AnswerRequestDto(quiz.getQuestions().get(i).getAnswers().get(j).getContent(),
                                new Question(question.id(), question.content(),question.correctAnswer(), question.answers(), question.quiz())));

                        questionService.updateQuestion(question.id(), new QuestionRequestDto(quiz.getQuestions().get(i).getContent(),answerDto.id().toString(),
                                new Quiz(quizDto.id(),quizDto.name(), quizDto.category(), quizDto.user(), quizDto.questions(), quizDto.games(), quizDto.status(), quizDto.createdAt()), null));
                    }
                    else
                    {
                        answerService.createAnswer(question.id(), new AnswerRequestDto(quiz.getQuestions().get(i).getAnswers().get(j).getContent(),
                                new Question(question.id(), question.content(),question.correctAnswer(), question.answers(), question.quiz())));
                    }
                }
            }

            redirectAttributes.addFlashAttribute("message", "Quiz successfully added");

            return "redirect:/admin/quizzes";
        }
        else
        {
            model.addAttribute("sameQuiz", "A quiz with the same name already exists in the database");

            return "admin/quiz/add";
        }
    }

    @GetMapping("{id}")
    public String editView(@PathVariable UUID id, Model model) {

        QuizDto quizDto = quizService.getQuiz(id);
        model.addAttribute("quiz", quizDto);

        List<CategoryDto> categoryDto = categoryService.getCategories();
        model.addAttribute("categoryList", categoryDto);

        return "admin/quiz/edit";
    }

    @PostMapping("{id}")
    public String edit(
            @PathVariable UUID id,
            @Valid @ModelAttribute("quiz") Quiz quiz,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
            if (!(quizService.getQuiz(quiz.getId()).name().equals(quiz.getName())) && !((quizService.checkIfQuizNameAvailable(quiz.getName())))) {

                    model.addAttribute("sameQuiz", "A quiz with the same name already exists in the database");

                    return "admin/quiz/edit";
            }
            else {
                    if (bindingResult.hasErrors()) {

                        return "admin/quiz/edit";
                    }
                    try {
                        CategoryDto category_local = categoryService.getCategory(quiz.getCategory().getName());

                        if (category_local.id() == null)
                        {
                            category_local = categoryService.createCategory(category_local);
                        }
                        QuizRequestDto quizRequestDto = new QuizRequestDto(quiz.getName(), new Category(category_local.id(),
                                category_local.name(),null), quiz.getUser(), null,quiz.getStatus(),
                                quiz.getCreatedAt(), quiz.getGames());

                        QuizDto quizDto = quizService.updateQuiz(id, quizRequestDto);

                        for (int i = 0; i < quiz.getQuestions().size(); i++)
                        {
                            QuestionDto question = questionService.updateQuestion(quiz.getQuestions().get(i).getId(),
                                                new QuestionRequestDto(quiz.getQuestions().get(i).getContent(), quiz.getQuestions().get(i).getCorrectAnswer(),
                                                new Quiz(quizDto.id(),quizDto.name(), quizDto.category(), quizDto.user(), quizDto.questions(), quizDto.games(),
                                                    quizDto.status(), quizDto.createdAt()), null));

                            for (int j = 0; j < quiz.getQuestions().get(i).getAnswers().size(); j++)
                            {
                                answerService.updateAnswer(quiz.getQuestions().get(i).getAnswers().get(j).getId(),
                                                new AnswerRequestDto(quiz.getQuestions().get(i).getAnswers().get(j).getContent(),
                                                new Question(question.id(), question.content(), question.correctAnswer(), question.answers(), question.quiz())));
                            }
                        }

                        redirectAttributes.addFlashAttribute("message", "Quiz successfully saved");

                    } catch (Exception ex) {

                        model.addAttribute("message", "An error occured. Quiz not saved");

                        return "admin/quiz/edit";
                    }

                    return "redirect:/admin/quizzes";
                }
    }

    @GetMapping("{id}/delete")
    public String deleteView(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        try {
            quizService.deleteQuiz(id);
            redirectAttributes.addFlashAttribute("message", "Quiz successfully deleted");

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "An error occured. Quiz not deleted");

            return "redirect:/admin/quizzes";
        }
        return "redirect:/admin/quizzes";
    }
}
