package pl.stormit.eduquiz.statistic.quizstatistic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;
import pl.stormit.eduquiz.statistic.quizstatistic.dto.QuizStatisticDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test"})
@SpringBootTest
class QuizStatisticFacadeImpIntegrationTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    QuizStatisticRepository quizStatisticRepository;

    @Autowired
    QuizStatisticFacadeImp quizStatisticFacadeImp;

    @Autowired
    QuizService quizService;

    @Autowired
    QuizRepository quizRepository;

    private static int score;
    private static final String USER_NAME = "user";
    private static final UUID USER_UUID = UUID.randomUUID();
    private static final Game game = new Game();
    private static final User user = new User();
    private static final QuizStatistic statistic = new QuizStatistic();

    @BeforeAll
    static void beforeAll() {
        score = 10;

        game.setId(UUID.randomUUID());
        game.setCreatedAt(LocalDateTime.now().minusSeconds(10));

        user.setId(USER_UUID);
        user.setNickname(USER_NAME);

        statistic.setGame(game);
        statistic.setUserId(user.getId());
        statistic.setScore(score);
        statistic.setDuration(ChronoUnit.SECONDS.between(game.getCreatedAt(), LocalDateTime.now()));
    }

    @AfterEach
    void afterEach() {
        quizRepository.deleteAll();
    }

    @Test
    void shouldReturnListWithThreeNewestQuizes() {
        // given
        QuizRequestDto quizRequestDto = new QuizRequestDto("name", null, null, null, null);
        quizService.createQuiz(quizRequestDto);
        quizService.createQuiz(quizRequestDto);
        quizService.createQuiz(quizRequestDto);

        // when
        List<QuizDto> quizDtoList = quizStatisticFacadeImp.getThreeNewest();

        // then
        assertNotNull(quizDtoList);
        assertEquals(quizDtoList.size(), 3);
    }

    @Test
    @WithMockUser
    void shouldReturnDtoWithUserIdWhenUserIsLogged() {
        // given
        when(userRepository.findUserByNickname(USER_NAME)).thenReturn(Optional.of(user));
        when(quizStatisticRepository.save(any(QuizStatistic.class))).thenReturn(statistic);

        // when
        QuizStatisticDto expectedDto = quizStatisticFacadeImp.addStatisticToDB(game, score);

        // then
        verify(userRepository, times(1)).findUserByNickname(USER_NAME);
        verify(quizStatisticRepository, times(1)).save(any(QuizStatistic.class));
        assertNotNull(expectedDto);
        assertEquals(expectedDto.game().getId(), game.getId());
        assertEquals(expectedDto.userId(), USER_UUID);
    }

    @Test
    void shouldReturnDtoWithoutUserIdWhenUserNotLogged() {
        // given + when
        QuizStatisticDto expectedDto = quizStatisticFacadeImp.addStatisticToDB(game, score);

        // then
        verify(userRepository, times(0)).findUserByNickname(USER_NAME);
        verify(quizStatisticRepository, times(0)).save(any(QuizStatistic.class));
        assertNotNull(expectedDto);
        assertNull(expectedDto.userId());
    }

}