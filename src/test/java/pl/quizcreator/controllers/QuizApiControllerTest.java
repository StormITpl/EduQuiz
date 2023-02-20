package pl.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.stormit.eduquiz.quizcreator.controllers.QuizApiController;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizCreationDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizEditingDto;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@WebMvcTest(QuizApiController.class)
class QuizApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuizService quizService;

    @Test
    void shouldReturnAllQuizzes() throws Exception {

        Quiz firstQuiz = new Quiz("Royal");
        Quiz secondQuiz = new Quiz("Special");

        given(quizService.getQuizzes()).willReturn(List.of(firstQuiz, secondQuiz));

        mockMvc.perform(get("/api/v1/quizzes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Royal")))
                .andExpect(content().string(containsString("Special")));

    }

    @Test
    void shouldReturnOneQuizById() throws Exception {

        Quiz quiz = new Quiz("Special");
        String url = "/api/v1/quizzes/" + quiz.getId();

        given(quizService.getQuiz(any())).willReturn(quiz);

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Special")));
    }

    @Test
    void shouldCreateQuiz() throws Exception {

        QuizCreationDto exemplaryQuizCreationDto = new QuizCreationDto(
                "Master", null, null, List.of());

        String dtoAsString = objectMapper.writeValueAsString(exemplaryQuizCreationDto);

        MockHttpServletRequestBuilder content = post("/api/v1/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoAsString);


        mockMvc.perform(content)
                .andExpect(status().isCreated());


    }

    @Test
    void shouldUpdateQuiz() throws Exception {

        Quiz exemplaryQuiz = new Quiz("Master");
        Category exemplaryCategory = new Category("Biology");
        Question exemplaryQuestion = new Question("Why");
        QuizEditingDto exemplaryQuizEditingDto = new QuizEditingDto(
                "Master", exemplaryCategory, List.of(exemplaryQuestion));

        MockHttpServletRequestBuilder content = put(
                "/api/v1/quizzes/{quizId}", exemplaryQuiz.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exemplaryQuizEditingDto));

        mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldDeleteQuiz() throws Exception {

        Quiz exemplaryQuiz = new Quiz("Special");

        MockHttpServletRequestBuilder content = delete(
                "/api/v1/quizzes/{quizId}", exemplaryQuiz.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
