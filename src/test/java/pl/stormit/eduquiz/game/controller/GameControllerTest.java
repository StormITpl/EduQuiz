package pl.stormit.eduquiz.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.stormit.eduquiz.common.exception.ErrorMessage;
import pl.stormit.eduquiz.game.contoller.GameController;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles({"test"})
@WebMvcTest(GameController.class)
class GameControllerTest {

    private static final UUID ID_1 = UUID.fromString("5d1b4c2c-9f1c-11ed-a8fc-0242ac120001");

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private GameDto gameDto;

    @Test
    void shouldReturn201WhenGameCreatedCorrectly() throws Exception {
        // given
        List<UUID> listExample = Collections.emptyList();
        gameDto = new GameDto(ID_1, listExample);
        given(gameService.createGame(any(QuizDto.class))).willReturn((new GameDto(ID_1, listExample)));

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/games/singleGame")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(gameDto))));

        // then
        result.andExpect(status().isCreated());
    }

    @Test
    void shouldReturn404WhenQuizIsNotFound() throws Exception {
        // given
        List<UUID> listExample = Collections.emptyList();
        gameDto = new GameDto(ID_1, listExample);
        given(gameService.createGame(any(QuizDto.class))).willThrow(new EntityNotFoundException("The quiz by id: " + ID_1 + ", does not exist."));

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/games/singleGame")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(gameDto))));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(resultIn -> {
                    String response = resultIn.getResponse().getContentAsString();
                    ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);
                    assertEquals("FAIL", errorMessage.getStatus());
                });
    }

    @Test
    void shouldReturn200WhenFoundGameByIdCorrectly() throws Exception {
        // given
        List<UUID> listExample = Collections.emptyList();
        gameDto = new GameDto(ID_1, listExample);
        given(gameService.getGame(any())).willReturn(gameDto);

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/games/" + ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(gameDto))));

        // then
        result.andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenGameIsNotFound() throws Exception {
        // given
        List<UUID> listExample = Collections.emptyList();
        gameDto = new GameDto(ID_1, listExample);
        given(gameService.getGame(any()))
                .willThrow(new EntityNotFoundException("The quiz by id: " + ID_1 + ", does not exist."));

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/games/" + ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(gameDto))));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(resultIn -> {
                    String response = resultIn.getResponse().getContentAsString();
                    ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);
                    assertEquals("FAIL", errorMessage.getStatus());
        });
    }

    @Test
    void shouldReturn204WhenGameDeletedCorrectly() throws Exception {
        // given
        List<UUID> listExample = Collections.emptyList();
        GameDto gameDto = new GameDto(ID_1, listExample);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/games/" + ID_1)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenGameIsNotFoundWhileDeleting() throws Exception {
        // given
        doThrow(EntityNotFoundException.class).when(gameService).deleteGame(isA(UUID.class));

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/games/" + ID_1)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound());
        result.andExpect(resultIn -> {
                    String response = resultIn.getResponse().getContentAsString();
                    ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);
                    assertEquals("FAIL", errorMessage.getStatus());
                });
    }

    @Test
    void shouldReturn400WhenPassWrongVariableInGetMethod() throws Exception {
        // when
        ResultActions result = mockMvc.perform(get("/api/v1/games/" + null)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isBadRequest());
        result.andExpect(resultIn -> {
            String response = resultIn.getResponse().getContentAsString();
            ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);
            assertEquals("FAIL", errorMessage.getStatus());
        });
    }

    @Test
    void shouldReturn400WhenGameDTOisNotValid() throws Exception {
        // given
        List<UUID> listExample = Collections.emptyList();
        GameDto gameDto = new GameDto(ID_1, listExample);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/games/" + ID_1)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent());
    }
}