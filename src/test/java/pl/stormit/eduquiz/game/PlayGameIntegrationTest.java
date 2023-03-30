package pl.stormit.eduquiz.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles({"test"})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayGameIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private QuizRepository quizRepository;

    @BeforeEach
    public void cleanTable() {
        gameRepository.deleteAll();
    }

    @Test
    void shouldCreateGameCorrectly() {
        //given
        Quiz quiz = new Quiz("First quiz");
        Quiz quizSaved = quizRepository.save(quiz);
        UUID quizId = quizSaved.getId();
        QuizDto quizDto = new QuizDto(quizId, "First quiz");

        //when
        HttpEntity<QuizDto> entity = new HttpEntity<>(quizDto);
        URI createGameUri = URI.create("/api/v1/games/singleGame");
        ResponseEntity<GameDto> responseEntity = restTemplate
                .exchange(createGameUri, HttpMethod.POST, entity, GameDto.class);
        UUID gameId = gameRepository.findAll().get(0).getId();
        GameDto body = responseEntity.getBody();

        //then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1, gameRepository.findAll().size());
        assertEquals(gameId, body.id());
    }
    
    @Test
    void shouldPassEntireGameCorrectly() {
        //given
        Quiz quiz = new Quiz("First quiz");
        Quiz quizSaved = quizRepository.save(quiz);
        UUID quizId = quizSaved.getId();
        QuizDto quizDto = new QuizDto(quizId, "First quiz");

        HttpEntity<QuizDto> entity = new HttpEntity<>(quizDto);
        URI createGameUri = URI.create("/api/v1/games/singleGame");
        restTemplate.exchange(createGameUri, HttpMethod.POST, entity, GameDto.class);
        UUID gameId = gameRepository.findAll().get(0).getId();

        AnswerDto firstAnswerDto = new AnswerDto(UUID.randomUUID(), "Abc", true);
        AnswerDto secondAnswerDto = new AnswerDto(UUID.randomUUID(), "Def", false);

        //when
        HttpEntity<AnswerDto> firstPlayEntity = new HttpEntity<>(firstAnswerDto);
        HttpEntity<AnswerDto> secondPlayEntity = new HttpEntity<>(secondAnswerDto);
        URI playUri = URI.create("/api/v1/games/" + gameId);
        restTemplate.exchange(playUri, HttpMethod.PUT, firstPlayEntity, GameDto.class);
        ResponseEntity<GameDto> playResponseEntity = restTemplate.exchange(playUri, HttpMethod.PUT, secondPlayEntity, GameDto.class);

        GameDto updatedGameDto = playResponseEntity.getBody();

        URI completeUri = URI.create("/api/v1/games/complete/" + gameId);
        ResponseEntity<GameDto> completeResponseEntity = restTemplate
                .exchange(completeUri, HttpMethod.PUT, entity, GameDto.class);

        Optional<Game> game = gameRepository.findById(gameId);

        //then
        assertEquals(HttpStatus.OK, playResponseEntity.getStatusCode());
        assertEquals(gameId, updatedGameDto.id());
        assertEquals(2, updatedGameDto.userAnswers().size());
        assertEquals(HttpStatus.OK, completeResponseEntity.getStatusCode());
        assertEquals(quizId, game.get().getQuiz().getId());
        assertEquals(2, game.get().getUserAnswers().size());
    }
}
