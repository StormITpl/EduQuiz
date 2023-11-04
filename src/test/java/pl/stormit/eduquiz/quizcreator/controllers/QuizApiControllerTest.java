package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.server.ResponseStatusException;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
class QuizApiControllerTest {

    private static final UUID FIRST_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0101");

    private static final UUID SECOND_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0202");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Test
    void shouldReturn200WhenFoundAllQuizzes() throws Exception {
        // given
        QuizDto firstQuizDto = new QuizDto(FIRST_ID, "PL", null, null, List.of(), List.of());
        QuizDto secondQuizDto = new QuizDto(SECOND_ID, "UK", null, null, List.of(), List.of());
        ArrayList<QuizDto> quizzesDtos = new ArrayList<>();
        quizzesDtos.add(firstQuizDto);
        quizzesDtos.add(secondQuizDto);
        given(quizService.getQuizzes()).willReturn(quizzesDtos);

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/quizzes")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(containsString("PL")))
                .andExpect(content().string(containsString("UK")));
    }

    @Test
    void shouldReturn404WhenQuizzesNotFound() throws Exception {
        // given
        given(quizService.getQuizzes()).willThrow(new EntityNotFoundException("Quizzes not found"));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/quizzes")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("Quizzes not found")));
    }

    @Test
    void shouldReturn200IfQuizIsFoundByIdCorrectly() throws Exception {
        // given
        QuizDto firstQuizDto = new QuizDto(FIRST_ID, "PL", null, null, List.of(), List.of());
        given(quizService.getQuiz(FIRST_ID)).willReturn(firstQuizDto);

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/quizzes/" + FIRST_ID).
                contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("PL")));
    }

    @Test
    void shouldReturn404WhenQuizNotFound() throws Exception {
        // given
        UUID nonExistentQuizId = UUID.randomUUID();
        given(quizService.getQuiz(nonExistentQuizId)).willThrow(new EntityNotFoundException("Quiz not found"));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/quizzes/" + nonExistentQuizId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("Quiz not found")));
    }

    @Test
    void shouldReturn201WhenQuizCreatedCorrectly() throws Exception {
        // given
        QuizRequestDto exemplaryQuizRequestDto = new QuizRequestDto("Master", null, null, List.of(), List.of());
        String dtoAsString = objectMapper.writeValueAsString(exemplaryQuizRequestDto);

        // when
        MockHttpServletRequestBuilder content = post("/api/v1/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoAsString);

        // then
        mockMvc.perform(content).andExpect(status().isCreated());
        verify(quizService, times(1)).createQuiz(eq(exemplaryQuizRequestDto));
    }

    @Test
    void shouldReturn400WhenQuizCreationFails() throws Exception {
        // given
        QuizRequestDto exemplaryQuizRequestDto = new QuizRequestDto("Master", null, null, List.of(), List.of());
        String dtoAsString = objectMapper.writeValueAsString(exemplaryQuizRequestDto);

        given(quizService.createQuiz(exemplaryQuizRequestDto)).willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz creation failed"));

        // when
        MockHttpServletRequestBuilder content = post("/api/v1/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoAsString);

        // then
        mockMvc.perform(content).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn200WhenQuizUpdatedCorrectly() throws Exception {
        // given
        QuizDto quizDto = new QuizDto(FIRST_ID, "Beginner", null, null, null, null);
        QuizRequestDto exemplaryQuizRequestDto = new QuizRequestDto("Master", null, null, null, null);

        // when
        MockHttpServletRequestBuilder content = put("/api/v1/quizzes/{quizId}", FIRST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exemplaryQuizRequestDto));

        // then
        mockMvc.perform(content)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(quizService, times(1)).updateQuiz(eq(FIRST_ID), eq(exemplaryQuizRequestDto));
    }

    @Test
    void shouldReturn404WhenQuizUpdateFails() throws Exception {
        // given
        QuizRequestDto exemplaryQuizRequestDto = new QuizRequestDto("Master", null, null, null, null);

        given(quizService.updateQuiz(eq(FIRST_ID), any(QuizRequestDto.class)))
                .willThrow(new EntityNotFoundException("Quiz not found"));

        // when
        MockHttpServletRequestBuilder content = put("/api/v1/quizzes/{quizId}", FIRST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exemplaryQuizRequestDto));

        // then
        mockMvc.perform(content)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn204WhenQuizDeletedCorrectly() throws Exception {
        // when
        MockHttpServletRequestBuilder content = delete("/api/v1/quizzes/{quizId}", FIRST_ID).contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(content).andExpect(status().isNoContent()).andDo(MockMvcResultHandlers.print());
        verify(quizService, times(1)).deleteQuiz(eq(FIRST_ID));
    }

    @Test
    void shouldReturn404WhenQuizDeletionFails() throws Exception {
        // given
        doThrow(new EntityNotFoundException("Quiz not found"))
                .when(quizService).deleteQuiz(FIRST_ID);

        // when
        MockHttpServletRequestBuilder content = delete("/api/v1/quizzes/{quizId}", FIRST_ID)
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(content)
                .andExpect(status().isNotFound());
    }
}
