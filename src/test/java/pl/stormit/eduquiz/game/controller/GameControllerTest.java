package pl.stormit.eduquiz.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.stormit.eduquiz.game.contoller.GameController;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.game.service.GameService;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private QuizDto quizDto;

    @Test
    void shouldReturnStatus201WhenGameCreatedSuccessfully() throws Exception {
        // given
        UUID quizId = UUID.randomUUID();
        QuizDto quizDto = new QuizDto(
                quizId, "Data structure",
                new Category(UUID.randomUUID(), "Programming", Collections.emptyList()),
                new User(UUID.randomUUID(), "Junior Developer", Collections.emptyList()), Collections.emptyList(), Collections.emptyList()
        );

        UUID gameId = UUID.randomUUID();
        GameDto createdGameDto = new GameDto(gameId, Collections.emptyList());

        when(gameService.createGame(quizDto)).thenReturn(createdGameDto);

        // when
        this.mockMvc.perform(post("/api/v1/games/singleGame")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(quizDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("message", "The new game has been successfully created"))
                .andExpect(jsonPath("$.id").value(createdGameDto.id().toString()));

        // then
        verify(gameService, times(1)).createGame(quizDto);
    }

    @Test
    void shouldReturnStatus400WhenGameCreationFails() throws Exception {
        // given
        UUID quizId = UUID.randomUUID();
        QuizDto quizDto = new QuizDto(quizId, "Data structure", new Category(UUID.randomUUID(), "Programming", Collections.emptyList()),
                new User(UUID.randomUUID(), "Junior Developer", Collections.emptyList()), Collections.emptyList(), Collections.emptyList());

        when(gameService.createGame(quizDto)).thenReturn(null);

        // when
        mockMvc.perform(post("/api/v1/games/singleGame")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(quizDto))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to create the game"));

        // then
        verify(gameService, times(1)).createGame(quizDto);
    }

    @Test
    void shouldReturnStatus200WhenFoundGameByIdCorrectly() throws Exception {
        // given
        UUID gameId = UUID.randomUUID();
        GameDto gameDto = new GameDto(gameId, Collections.emptyList());
        when(gameService.getGame(gameId)).thenReturn(gameDto);

        // when
        mockMvc.perform(get("/api/v1/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("message", "The Game has been successfully found"))
                .andExpect(jsonPath("$.id").value(gameId.toString()));

        // then
        verify(gameService, times(1)).getGame(gameId);
    }

    @Test
    void shouldReturnStatus404WhenGameNotFound() throws Exception {
        // given
        UUID nonExistentGameId = UUID.randomUUID();
        when(gameService.getGame(nonExistentGameId)).thenReturn(null);

        // when
        mockMvc.perform(get("/api/v1/games/" + nonExistentGameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Game not found"));

        // then
        verify(gameService, times(1)).getGame(nonExistentGameId);
    }

    @Test
    void shouldReturnStatus200WhenGamePlayedSuccessfully() throws Exception {
        // given
        UUID gameId = UUID.randomUUID();
        AnswerDto answerDto = new AnswerDto(UUID.randomUUID(), "Comparable", false, null);
        GameDto updatedGameDto = new GameDto(gameId, Collections.emptyList());

        when(gameService.playGame(gameId, answerDto)).thenReturn(updatedGameDto);

        // when
        mockMvc.perform(put("/api/v1/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerDto)))
                .andExpect(status().isOk())
                .andExpect(header().string("message", "The game has been successfully started"))
                .andExpect(jsonPath("$.id").value(gameId.toString()));

        // then
        verify(gameService, times(1)).playGame(gameId, answerDto);
    }

    @Test
    void shouldReturnStatus404WhenGameNotFoundAndPlayGameFails() throws Exception {
        // given
        UUID nonExistentGameId = UUID.randomUUID();
        when(gameService.playGame(eq(nonExistentGameId), any(AnswerDto.class))).thenReturn(null);

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/games/" + nonExistentGameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AnswerDto.builder().build())));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(content().string("Game not found"));

        verify(gameService, times(1)).playGame(eq(nonExistentGameId), any(AnswerDto.class));
    }

    @Test
    void shouldReturnStatus200WhenGameCompletedSuccessfully() throws Exception {
        // given
        UUID gameId = UUID.randomUUID();
        AnswerDto answerDto = new AnswerDto(UUID.randomUUID(), "Comparable", false, null);
        GameDto updatedGameDto = new GameDto(gameId, Collections.emptyList());

        when(gameService.playGame(eq(gameId), any(AnswerDto.class))).thenReturn(updatedGameDto);

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/games/" + gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(answerDto)));

        // then
        result.andExpect(status().isOk())
                .andExpect(header().string("message", "The game has been successfully started"))
                .andExpect(jsonPath("$.id").value(gameId.toString()));

        verify(gameService, times(1)).playGame(eq(gameId), any(AnswerDto.class));
    }

    @Test
    void shouldReturnStatus404WhenGameNotFoundAndCompleteGameFails() throws Exception {
        // given
        UUID nonExistentGameId = UUID.randomUUID();
        when(gameService.completeGame(eq(nonExistentGameId))).thenReturn(null);

        // when
        ResultActions result = mockMvc.perform(put("/api/v1/games/complete/" + nonExistentGameId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(content().string("Game not found"));

        verify(gameService, times(1)).completeGame(eq(nonExistentGameId));
    }

    @Test
    void shouldReturnStatus204WhenGameDeletedSuccessfully() throws Exception {
        // given
        UUID gameId = UUID.randomUUID();
        when(gameService.deleteGame(eq(gameId))).thenReturn(true);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/games/" + gameId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent())
                .andExpect(header().string("message", "The game has been successfully deleted"));

        verify(gameService, times(1)).deleteGame(eq(gameId));
    }

    @Test
    void shouldReturnStatus404WhenGameNotFoundAndDeleteGameFails() throws Exception {
        // given
        UUID nonExistentGameId = UUID.randomUUID();
        when(gameService.deleteGame(eq(nonExistentGameId))).thenReturn(false);

        // when
        ResultActions result = mockMvc.perform(delete("/api/v1/games/" + nonExistentGameId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound())
                .andExpect(content().string("Game not found"));

        verify(gameService, times(1)).deleteGame(eq(nonExistentGameId));
    }
}