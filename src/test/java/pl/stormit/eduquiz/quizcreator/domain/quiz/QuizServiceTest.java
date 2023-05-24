package pl.stormit.eduquiz.quizcreator.domain.quiz;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class QuizServiceTest {
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizRequestMapper quizRequestMapper;

    @Test
    void shouldReturnTwoQuizzes() {
        // given
        Quiz firstQuiz = new Quiz();
        firstQuiz.setName("Gold");
        QuizRequestDto firstQuizRequestDto = quizRequestMapper.mapQuizEntityToQuizRequestDto(firstQuiz);
        quizService.createQuiz(firstQuizRequestDto);

        Quiz secondQuiz = new Quiz();
        secondQuiz.setName("Silver");
        QuizRequestDto secondQuizRequestDto = quizRequestMapper.mapQuizEntityToQuizRequestDto(secondQuiz);
        quizService.createQuiz(secondQuizRequestDto);

        // when
        List<QuizDto> quizzesDto = quizService.getQuizzes();

        // then
        assertThat(quizzesDto).hasSize(2)
                .extracting(QuizDto::name).containsExactlyInAnyOrder("Gold", "Silver");
    }

    @Test
    void shouldReturnOneQuizFoundById() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Special");
        QuizRequestDto quizRequestDto = quizRequestMapper.mapQuizEntityToQuizRequestDto(quiz);
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);


        // when
        QuizDto quizDtoFoundById = quizService.getQuiz(createdQuiz.id());

        // then
        assertEquals(quizDtoFoundById.id(), createdQuiz.id());
        assertEquals(quizDtoFoundById.name(), createdQuiz.name());
    }

    @Test
    void shouldCreateQuiz() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Special");
        QuizRequestDto quizRequestDto = new QuizRequestDto(
                quiz.getName(),
                quiz.getCategory(),
                quiz.getUser(),
                quiz.getQuestions(),
                quiz.getGames());

        // when
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);

        // then
        assertEquals(createdQuiz.name(), quiz.getName());
        assertNotNull(createdQuiz.id());
    }

    @Test
    void shouldUpdateQuiz() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Special");
        QuizRequestDto quizRequestDto = quizRequestMapper.mapQuizEntityToQuizRequestDto(quiz);
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);
        QuizRequestDto quizToUpdate = new QuizRequestDto("Pro",
                quiz.getCategory(),
                quiz.getUser(),
                quiz.getQuestions(),
                quiz.getGames());

        // when
        QuizDto updatedQuiz = quizService.updateQuiz(createdQuiz.id(), quizToUpdate);

        // then
        assertEquals(updatedQuiz.name(), "Pro");
        assertNull(updatedQuiz.category());
        assertNull(updatedQuiz.questions());
    }

    @Test
    void shouldDeleteQuiz() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Special");
        QuizRequestDto quizRequestDto = quizRequestMapper.mapQuizEntityToQuizRequestDto(quiz);
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);

        // when
        quizService.deleteQuiz(createdQuiz.id());

        // then
        assertThrows(EntityNotFoundException.class, () -> quizService.getQuiz(createdQuiz.id()));
    }
}
