package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@WebMvcTest(AnswerApiController.class)
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
        //given
        AnswerDto firstAnswer = new AnswerDto(FIRST_ANSWER_ID, "Poland", true, null);
        AnswerDto secondAnswer = new AnswerDto(SECOND_ANSWER_ID, "Spain", false, null);
        given(answerService.getAnswers(any())).willReturn(List.of(firstAnswer, secondAnswer));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + QUESTION_ID + "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(List.of(firstAnswer, secondAnswer)))));
        ;

        //then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Poland")));
        result.andExpect(content().string(containsString("Spain")));
    }

    @Test
    void shouldReturn200WhenFoundAnswerByIdCorrectly() throws Exception {
        //given
        AnswerDto firstAnswer = new AnswerDto(FIRST_ANSWER_ID, "Poland", true, null);
        given(answerService.getAnswer(FIRST_ANSWER_ID)).willReturn(firstAnswer);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + QUESTION_ID + "/answers/" + FIRST_ANSWER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(firstAnswer))));

        //then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Poland")));
    }

    @Test
    void shouldReturn201WhenAnswerCreatedCorrectly() throws Exception {
        //given
        AnswerRequestDto answerRequestDto = new AnswerRequestDto("Poland", true, null);
        given(answerService.createAnswer(QUESTION_ID, answerRequestDto))
                .willReturn(new AnswerDto(FIRST_ANSWER_ID,"Poland", true, null));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/questions/" + QUESTION_ID + "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(answerRequestDto))));

        //then
        result.andExpect(status().isCreated());
        result.andExpect(content().string(containsString("Poland")));
    }

    @Test
    void shouldReturn200WhenAnswerUpdatedCorrectly() throws Exception {
        //given
        AnswerRequestDto answerRequestDto = new AnswerRequestDto( "Poland", true, null);
        given(answerService.updateAnswer(FIRST_ANSWER_ID, answerRequestDto))
                .willReturn(new AnswerDto(FIRST_ANSWER_ID, "Spain", false, null));

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/questions/" + QUESTION_ID + "/answers/" + FIRST_ANSWER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(answerRequestDto))));

        //then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Spain")));
    }

    @Test
    void shouldReturn204WhenAnswerDeletedCorrectly() throws Exception {
        //given
        AnswerDto answerDto = new AnswerDto(FIRST_ANSWER_ID, "Poland", true, null);

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/questions/" + QUESTION_ID + "/answers/" + FIRST_ANSWER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(answerDto))));

        //then
        result.andExpect(status().isNoContent());
    }
}