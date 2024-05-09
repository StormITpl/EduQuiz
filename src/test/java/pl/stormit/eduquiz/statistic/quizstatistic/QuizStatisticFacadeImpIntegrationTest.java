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
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizRepository;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;
import pl.stormit.eduquiz.statistic.quizstatistic.dto.QuizStatisticDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    private static final Game game2 = new Game();
    private static final Game game3 = new Game();
    private static final Game game4 = new Game();
    private static final Quiz quiz = new Quiz();
    private static final Quiz quiz2 = new Quiz();
    private static final User user = new User();
    private static final QuizStatistic statistic = new QuizStatistic();
    private static final QuizStatistic statistic2 = new QuizStatistic();
    private static final QuizStatistic statistic3 = new QuizStatistic();
    private static final QuizStatistic statistic4 = new QuizStatistic();

    @BeforeAll
    static void beforeAll() {
        score = 10;

        quiz.setId(UUID.randomUUID());
        quiz.setName("Quiz1");

        quiz2.setId(UUID.randomUUID());
        quiz2.setName("Quiz2");

        game.setId(UUID.randomUUID());
        game.setQuiz(quiz);
        game.setCreatedAt(LocalDateTime.now().minusSeconds(10));

        game2.setId(UUID.randomUUID());
        game2.setQuiz(quiz);
        game2.setCreatedAt(LocalDateTime.now().minusSeconds(20));

        game3.setId(UUID.randomUUID());
        game3.setQuiz(quiz2);
        game3.setCreatedAt(LocalDateTime.now().minusSeconds(11));

        game4.setId(UUID.randomUUID());
        game4.setQuiz(quiz2);
        game4.setCreatedAt(LocalDateTime.now().minusSeconds(21));

        user.setId(USER_UUID);
        user.setNickname(USER_NAME);

        statistic.setGame(game);
        statistic.setUserId(user.getId());
        statistic.setScore(score);
        statistic.setDuration(ChronoUnit.SECONDS.between(game.getCreatedAt(), LocalDateTime.now()));

        statistic2.setGame(game2);
        statistic2.setUserId(user.getId());
        statistic2.setScore(score + 10);
        statistic2.setDuration(ChronoUnit.SECONDS.between(game2.getCreatedAt(), LocalDateTime.now()));

        statistic3.setGame(game3);
        statistic3.setUserId(user.getId());
        statistic3.setScore(score);
        statistic3.setDuration(ChronoUnit.SECONDS.between(game3.getCreatedAt(), LocalDateTime.now()));

        statistic4.setGame(game4);
        statistic4.setUserId(user.getId());
        statistic4.setScore(score + 10);
        statistic4.setDuration(ChronoUnit.SECONDS.between(game4.getCreatedAt(), LocalDateTime.now()));
    }

    @AfterEach
    void afterEach() {
        quizRepository.deleteAll();
    }

    @Test
    void shouldReturnListWithThreeNewestQuizzes() {
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

    @Test
    void shouldReturnMapWithBestTime() {
        // given
        given(quizStatisticRepository.findAll()).willReturn(List.of(statistic, statistic2, statistic3, statistic4));

        // when
        Map<String, Long> durationForEachQuizzes = quizStatisticFacadeImp.getDurationForEachQuiz(true);

        //then
        verify(quizStatisticRepository, times(1)).findAll();
        assertNotNull(durationForEachQuizzes);
        assertEquals(2, durationForEachQuizzes.size());
        assertEquals(10L, durationForEachQuizzes.get("Quiz1"));
        assertEquals(11L, durationForEachQuizzes.get("Quiz2"));
        assertNotEquals(20L, durationForEachQuizzes.get("Quiz1"));
        assertNotEquals(21L, durationForEachQuizzes.get("Quiz2"));
    }

    @Test
    void shouldReturnMapWithWorstTime() {
        // given
        given(quizStatisticRepository.findAll()).willReturn(List.of(statistic, statistic2, statistic3, statistic4));

        // when
        Map<String, Long> durationForEachQuizzes = quizStatisticFacadeImp.getDurationForEachQuiz(false);

        //then
        verify(quizStatisticRepository, times(1)).findAll();
        assertNotNull(durationForEachQuizzes);
        assertEquals(2, durationForEachQuizzes.size());
        assertEquals(20L, durationForEachQuizzes.get("Quiz1"));
        assertEquals(21L, durationForEachQuizzes.get("Quiz2"));
        assertNotEquals(10L, durationForEachQuizzes.get("Quiz1"));
        assertNotEquals(20L, durationForEachQuizzes.get("Quiz2"));
    }

    @Test
    void shouldReturnMapWithCorrectOrderForBestDuration() {
        // given
        given(quizStatisticRepository.findAll()).willReturn(List.of(statistic, statistic2, statistic3, statistic4));

        // when
        Map<String, Long> durationForEachQuizzes = quizStatisticFacadeImp.getDurationForEachQuiz(true);
        List<String> expectedKeys = Arrays.asList("Quiz1", "Quiz2");
        List<String> actualKeys = new ArrayList<>(durationForEachQuizzes.keySet());
        List<Long> expectedValues = Arrays.asList(10L, 11L);
        List<Long> actualValues = new ArrayList<>(durationForEachQuizzes.values());

        //then
        verify(quizStatisticRepository, times(1)).findAll();
        assertThat(expectedKeys).containsExactlyElementsOf(actualKeys);
        assertThat(expectedValues).containsExactlyElementsOf(actualValues);
        assertEquals(expectedKeys, actualKeys);
    }

    @Test
    void shouldReturnMapWithCorrectOrderForWorstDuration() {
        // given
        given(quizStatisticRepository.findAll()).willReturn(List.of(statistic, statistic2, statistic3, statistic4));

        // when
        Map<String, Long> durationForEachQuizzes = quizStatisticFacadeImp.getDurationForEachQuiz(false);
        List<String> expectedKeys = Arrays.asList("Quiz2", "Quiz1");
        List<String> actualKeys = new ArrayList<>(durationForEachQuizzes.keySet());
        List<Long> expectedValues = Arrays.asList(21L, 20L);
        List<Long> actualValues = new ArrayList<>(durationForEachQuizzes.values());

        //then
        verify(quizStatisticRepository, times(1)).findAll();
        assertThat(expectedKeys).containsExactlyElementsOf(actualKeys);
        assertThat(expectedValues).containsExactlyElementsOf(actualValues);
    }
}