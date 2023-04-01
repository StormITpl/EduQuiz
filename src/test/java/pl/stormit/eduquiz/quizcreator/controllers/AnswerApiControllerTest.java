package pl.stormit.eduquiz.quizcreator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.answer.AnswerService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void shouldReturnListOfAnswersByQuestionId() throws Exception {
        //given
        AnswerDto firstAnswer = new AnswerDto(firstAnswerId, "Poland", true);
        AnswerDto secondAnswer = new AnswerDto(secondAnswerId, "Spain", false);
        given(answerService.getAnswers(any())).willReturn(List.of(firstAnswer, secondAnswer));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/questions/" + questionId + "/answers"));

        //then
        result.andExpect(status().isOk());
        result.andExpect(content().string(containsString("Poland")));
        result.andExpect(content().string(containsString("Spain")));
    }

    @Test
    void getAnswer() {
        //given

        //when

        //then
    }

    @Test
    void createAnswer() {
        //given

        //when

        //then
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