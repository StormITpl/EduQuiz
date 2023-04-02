package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test"})
@WebMvcTest(AnswerApiController.class)
class AnswerApiControllerTest {

    private static final UUID questionId = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0117");
    private static final UUID firstAnswerId = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0221");
    private static final UUID secondAnswerId = UUID.fromString("a92315cb-5862-4449-9826-ca09c76e0123");

    @MockBean
    private AnswerService answerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnStatusOkWhenFoundListOfAnswersByQuestionIdCorrectly() throws Exception {
        //given
        AnswerDto firstAnswer = new AnswerDto(firstAnswerId, "Poland", true);
        AnswerDto secondAnswer = new AnswerDto(secondAnswerId, "Spain", false);
        given(answerService.getAnswers(any())).willReturn(List.of(firstAnswer, secondAnswer));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + questionId + "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(List.of(firstAnswer, secondAnswer)))));
        ;

        //then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Poland")));
        result.andExpect(content().string(containsString("Spain")));
    }

    @Test
    void shouldReturnStatusOkWhenFoundAnswerByIdCorrectly() throws Exception {
        //given
        AnswerDto firstAnswer = new AnswerDto(firstAnswerId, "Poland", true);
        given(answerService.getAnswer(firstAnswerId)).willReturn(firstAnswer);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + questionId + "/answers/" + firstAnswerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(firstAnswer))));

        //then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Poland")));
    }

    @Test
    void shouldReturnStatusCreatedWhenAnswerCreatedCorrectly() throws Exception {
        //given
        AnswerDto answerDto = new AnswerDto(firstAnswerId, "Poland", true);
        Question question = new Question("In which country was Nicolaus Copernicus born");
        UUID questionId = question.getId();
        given(answerService.createAnswer(questionId, answerDto)).willReturn(answerDto);

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/questions/" + questionId + "/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(answerDto))));

        //then
        result.andExpect(status().isCreated());
        result.andExpect(content().string(containsString("Poland")));
    }

    @Test
    void updateAnswer() {
        //given

        //when

        //then
    }

    @Test
    void deleteAnswer() {
        //given

        //when

        //then
    }
}