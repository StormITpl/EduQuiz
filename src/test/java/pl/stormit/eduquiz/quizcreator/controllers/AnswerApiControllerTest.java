package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerRequestDto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
class AnswerApiControllerTest {

    private static final UUID QUESTION_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0117");
    private static final UUID FIRST_ANSWER_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0221");
    private static final UUID SECOND_ANSWER_ID = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0123");

    @MockBean
    private AnswerService answerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn200WhenFoundListOfAnswersByQuestionIdCorrectly() throws Exception {
        // given
        AnswerDto firstAnswer = new AnswerDto(FIRST_ANSWER_ID, "Poland", true, null);
        AnswerDto secondAnswer = new AnswerDto(SECOND_ANSWER_ID, "Spain", false, null);
        given(answerService.getAnswers(any())).willReturn(List.of(firstAnswer, secondAnswer));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + QUESTION_ID + "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(List.of(firstAnswer, secondAnswer)))));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Poland")));
        result.andExpect(content().string(containsString("Spain")));
    }

    @Test
    void shouldReturn404WhenQuestionIdNotFound() throws Exception {
        // given
        UUID nonExistentQuestionId = UUID.randomUUID();
        given(answerService.getAnswers(nonExistentQuestionId)).willThrow(new EntityNotFoundException("Question not found"));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + nonExistentQuestionId + "/answers")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("Question not found")));
    }

    @Test
    void shouldReturn200WhenFoundAnswerByIdCorrectly() throws Exception {
        // given
        AnswerDto firstAnswer = new AnswerDto(FIRST_ANSWER_ID, "Poland", true, null);
        given(answerService.getAnswer(FIRST_ANSWER_ID)).willReturn(firstAnswer);

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + QUESTION_ID + "/answers/" + FIRST_ANSWER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(firstAnswer))));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Poland")));
    }

    @Test
    void shouldReturn404WhenAnswerNotFound() throws Exception {
        // given
        UUID nonExistentAnswerId = UUID.randomUUID();
        given(answerService.getAnswer(nonExistentAnswerId)).willThrow(new EntityNotFoundException("Answer not found"));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + QUESTION_ID + "/answers/" + nonExistentAnswerId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("Answer not found")));
    }

    @Test
    void shouldReturn201WhenAnswerCreatedCorrectly() throws Exception {
        // given
        AnswerRequestDto answerRequestDto = new AnswerRequestDto("Poland", true, null);
        given(answerService.createAnswer(QUESTION_ID, answerRequestDto)).willReturn(new AnswerDto(FIRST_ANSWER_ID, "Poland", true, null));

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/questions/" + QUESTION_ID + "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(answerRequestDto))));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(content().string(containsString("Poland")));
    }

    @Test
    void shouldReturn404WhenCreatingAnswerForNonExistentQuestion() throws Exception {
        // given
        UUID nonExistentQuestionId = UUID.randomUUID();
        AnswerRequestDto answerRequestDto = new AnswerRequestDto("Poland", true, null);

        given(answerService.createAnswer(nonExistentQuestionId, answerRequestDto))
                .willThrow(new EntityNotFoundException("The question by id: " + nonExistentQuestionId + ", does not exist."));

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/questions/" + nonExistentQuestionId + "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(answerRequestDto))));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("The question by id: " + nonExistentQuestionId + ", does not exist.")));
    }

    @Test
    void shouldReturn200WhenAnswerUpdatedCorrectly() throws Exception {
        // given
        AnswerRequestDto answerRequestDto = new AnswerRequestDto("Poland", true, null);
        given(answerService.updateAnswer(FIRST_ANSWER_ID, answerRequestDto)).willReturn(new AnswerDto(FIRST_ANSWER_ID, "Spain", false, null));

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/questions/" + QUESTION_ID + "/answers/" + FIRST_ANSWER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(answerRequestDto))));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Spain")));
    }

    @Test
    void shouldReturn404WhenInvalidAnswerDataCausesEntityNotFound() throws Exception {
        // given
        AnswerRequestDto invalidAnswerRequestDto = new AnswerRequestDto(null, true, null);
        given(answerService.updateAnswer(FIRST_ANSWER_ID, invalidAnswerRequestDto))
                .willThrow(new EntityNotFoundException("Invalid answer data"));

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/questions/" + QUESTION_ID + "/answers/" + FIRST_ANSWER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(invalidAnswerRequestDto))));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("Invalid answer data")));
    }

    @Test
    void shouldReturn204WhenAnswerDeletedCorrectly() throws Exception {
        // given
        AnswerDto answerDto = new AnswerDto(FIRST_ANSWER_ID, "Poland", true, null);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/questions/" + QUESTION_ID + "/answers/" + FIRST_ANSWER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(answerDto))));

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenAnswerDeletionFails() throws Exception {
        // given
        UUID nonExistentAnswerId = UUID.randomUUID();
        doThrow(new EntityNotFoundException("Answer not found")).when(answerService).deleteAnswer(nonExistentAnswerId);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/questions/" + QUESTION_ID + "/answers/" + nonExistentAnswerId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(content().string(containsString("Answer not found")));
    }
}