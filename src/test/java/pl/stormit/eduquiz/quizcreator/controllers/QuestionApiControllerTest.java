package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionService;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
class QuestionApiControllerTest {

    private static final UUID FIRST_QUESTION_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0112");

    private static final UUID SECOND_QUESTION_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0777");

    @MockBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn200WhenFoundListOfQuestionsCorrectly() throws Exception {
        //given
        QuestionDto firstQuestion = new QuestionDto(FIRST_QUESTION_ID, "In what year did World War II begin?", null, null);
        QuestionDto secondQuestion = new QuestionDto(SECOND_QUESTION_ID, "What was the name of the First Historical Era?", null, null);
        given(questionService.getQuestions()).willReturn(List.of(firstQuestion, secondQuestion));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(List.of(firstQuestion, secondQuestion)))));

        //then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("In what year did World War II begin?")));
        result.andExpect(content().string(containsString("What was the name of the First Historical Era?")));
    }

    @Test
    void shouldReturn200WhenFoundQuestionByIdCorrectly() throws Exception {
        //given
        QuestionDto firstQuestion = new QuestionDto(FIRST_QUESTION_ID, "In what year did World War II begin?", null, null);
        given(questionService.getQuestion(FIRST_QUESTION_ID)).willReturn(firstQuestion);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + FIRST_QUESTION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(firstQuestion))));

        //then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("In what year did World War II begin?")));
    }

    @Test
    void shouldReturn201WhenQuestionCreatedCorrectly() throws Exception {
        // given
        QuestionRequestDto questionRequestDto = new QuestionRequestDto("In what year did World War II begin?", null, null);
        given(questionService.createQuestion(questionRequestDto))
                .willReturn(new QuestionDto(FIRST_QUESTION_ID, "In what year did World War II begin?", null, null));

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(questionRequestDto))));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(content().string(containsString("In what year did World War II begin?")));
    }

    @Test
    void shouldReturn200WhenQuestionUpdatedCorrectly() throws Exception {
        // given
        QuestionRequestDto questionRequestDto = new QuestionRequestDto("In what year did World War II begin?", null, null);
        given(questionService.updateQuestion(FIRST_QUESTION_ID, questionRequestDto))
                .willReturn(new QuestionDto(FIRST_QUESTION_ID, "What was the name of the First Historical Era?", null, null));

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/questions/" + FIRST_QUESTION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(questionRequestDto))));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("What was the name of the First Historical Era?")));
    }

    @Test
    void shouldReturn204WhenQuestionDeletedCorrectly() throws Exception {
        // given
        QuestionDto firstQuestion = new QuestionDto(FIRST_QUESTION_ID, "In what year did World War II begin?", null, null);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/questions/" + FIRST_QUESTION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(firstQuestion))));

        // then
        result.andExpect(status().isNoContent());
    }
}