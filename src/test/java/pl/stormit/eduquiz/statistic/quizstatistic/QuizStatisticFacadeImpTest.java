package pl.stormit.eduquiz.statistic.quizstatistic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.quiz.QuizService;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.statistic.quizstatistic.dto.QuizStatisticDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizStatisticFacadeImpTest {

    @Mock
    private QuizService quizService;

    @Mock
    private QuizStatisticService quizStatisticService;

    @InjectMocks
    private QuizStatisticFacadeImp quizStatisticFacadeImp;

    private static Game game = new Game();

    @BeforeAll
    static void beforeAll() {
        game.setId(UUID.randomUUID());
        game.setCreatedAt(LocalDateTime.now().minusSeconds(10));
    }

    @Test
    void shouldReturnThreeNewestQuizDtoList() {
        // given
        QuizDto quizDto = new QuizDto(null, null, null, null, null, null);
        List<QuizDto> quizes = List.of(quizDto, quizDto, quizDto);
        when(quizService.getThreeNewest()).thenReturn(quizes);

        // when
        List<QuizDto> expextedList = quizStatisticFacadeImp.getThreeNewest();

        // then
        verify(quizService).getThreeNewest();
        assertEquals(expextedList.size(), quizes.size());
        assertEquals(expextedList.size(), 3);
    }

    @Test
    void shouldReturnDtoWithCorrectData() {
        // given
        QuizStatisticDto quizStatisticDto = new QuizStatisticDto(UUID.randomUUID(), game, UUID.randomUUID(), 10,10, LocalDateTime.now());
        when(quizStatisticService.addStatisticToDB(any(Game.class), anyInt())).thenReturn(quizStatisticDto);

        // when
        QuizStatisticDto dto = quizStatisticFacadeImp.addStatisticToDB(game, 0);

        // then
        verify(quizStatisticService, times(1)).addStatisticToDB(any(Game.class), anyInt());
        assertEquals(dto.game().getId(), game.getId());
    }

}