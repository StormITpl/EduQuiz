package pl.stormit.eduquiz.result.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.dto.GameIdDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.result.dto.ResultDto;
import pl.stormit.eduquiz.result.service.ResultService;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
class ResultApiControllerTest {

    private static final UUID EXEMPLARY_ID = UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82502");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResultService resultService;

    @Test
    void shouldReturn201WhenResultCreatedCorrectly() throws Exception {
        // given
        Quiz exemplaryQuiz = new Quiz();
        exemplaryQuiz.setName("Minerals");
        Game exemplaryGame = new Game(exemplaryQuiz);
        ResultDto exemplaryResultDto = new ResultDto(EXEMPLARY_ID, exemplaryGame);
        GameIdDto exemplaryGameIdDto = new GameIdDto(EXEMPLARY_ID);
        given(resultService.createResult(exemplaryGameIdDto)).willReturn(exemplaryResultDto);

        // when
        MockHttpServletRequestBuilder content = post("/api/v1/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exemplaryGameIdDto));

        // then
        mockMvc.perform(content)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(EXEMPLARY_ID.toString()))
                .andExpect(jsonPath("$.game.quiz.name").value("Minerals"));
    }

    @Test
    void shouldReturnErrorResponseWhenResultCreationFails() throws Exception {
        // given
        GameIdDto invalidGameIdDto = new GameIdDto(null);

        // when
        MockHttpServletRequestBuilder content = post("/api/v1/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidGameIdDto));

        // then
        mockMvc.perform(content)
                .andExpect(status().isBadRequest())
                .andExpect(header().string("message", "Invalid input data"));
    }

    @Test
    void shouldReturn200WhenFoundResultByIdCorrectly() throws Exception {
        // given
        Quiz exemplaryQuiz = new Quiz();
        exemplaryQuiz.setName("History");
        Game exemplaryGame = new Game(exemplaryQuiz);
        ResultDto exemplaryResultDto = new ResultDto(EXEMPLARY_ID, exemplaryGame);
        given(resultService.getResult(EXEMPLARY_ID)).willReturn(exemplaryResultDto);

        // when
        MockHttpServletRequestBuilder content = get("/api/v1/results/" + exemplaryResultDto.id())
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(content).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EXEMPLARY_ID.toString()))
                .andExpect(jsonPath("$.game.quiz.name").value("History"));
    }

    @Test
    void shouldReturn404WhenResultNotFound() throws Exception {
        // given
        UUID nonExistentResultId = UUID.randomUUID();
        given(resultService.getResult(nonExistentResultId)).willReturn(null);

        // when
        MockHttpServletRequestBuilder request = get("/api/v1/results/" + nonExistentResultId);

        // then
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").doesNotHaveJsonPath());
    }

    @Test
    void shouldReturn204WhenResultDeletedCorrectly() throws Exception {
        // given
        ResultDto exemplaryResultDto = new ResultDto(EXEMPLARY_ID, null);

        // when
        MockHttpServletRequestBuilder content = delete("/api/v1/results/" + exemplaryResultDto.id()).contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(content).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn204WhenResultNotFoundDuringDeletion() throws Exception {
        // given
        UUID nonExistentResultId = UUID.randomUUID();
        given(resultService.getResult(nonExistentResultId)).willReturn(null);

        // when
        MockHttpServletRequestBuilder request = delete("/api/v1/results/" + nonExistentResultId);

        // then
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }
}
