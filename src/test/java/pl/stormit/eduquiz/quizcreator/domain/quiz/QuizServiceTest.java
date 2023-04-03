package pl.stormit.eduquiz.quizcreator.domain.quiz;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class QuizServiceTest {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    @Test
    void shouldReturnTwoQuizzes() {
        //given
        quizRepository.findAll();
        Quiz firstQuiz = new Quiz();
        firstQuiz.setName("Gold");
        quizRepository.save(firstQuiz);
        Quiz secondQuiz = new Quiz();
        secondQuiz.setName("Silver");
        quizRepository.save(secondQuiz);

        //when
        List<QuizDto> quizzesDto = quizService.getQuizzes();

        //then
        assertThat(quizzesDto)
                .hasSize(2)
                .extracting(QuizDto::name)
                .containsExactlyInAnyOrder("Gold", "Silver");
    }

    @Test
    void shouldReturnOneQuizFoundById() {
        //given
        Quiz quiz = new Quiz();
        quiz.setName("Special");
        Quiz savedQuiz = quizRepository.save(quiz);

        //when
        QuizDto quizDtoFoundById = quizService.getQuiz(savedQuiz.getId());

        //then
        assertEquals(quizDtoFoundById.id(), savedQuiz.getId());
        assertEquals(quizDtoFoundById.name(), savedQuiz.getName());
    }

    @Test
    void shouldCreateQuiz() {
        //given
        Quiz quiz = new Quiz();
        quiz.setName("Special");
        QuizRequestDto quizRequestDto = new QuizRequestDto(
                quiz.getName(), quiz.getCategory(), quiz.getUser(), quiz.getQuestions(), quiz.getGames());

        //when
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);

        //then
        assertEquals(createdQuiz.name(), quiz.getName());
        assertNotNull(createdQuiz.id());
    }

    @Test
    void shouldUpdateQuiz() {
        //given
        Quiz quiz = new Quiz();
        quiz.setName("Special");
        quiz.setQuestions(List.of());
        quizRepository.save(quiz);
        QuizRequestDto quizToUpdate = new QuizRequestDto("Pro", quiz.getCategory(),
                quiz.getUser(), quiz.getQuestions(), quiz.getGames());

        //when
        QuizDto updatedQuiz = quizService.updateQuiz(quiz.getId(), quizToUpdate);

        //then
        assertEquals(updatedQuiz.name(), "Pro");
        assertNull(updatedQuiz.category());
        assertEquals(updatedQuiz.questions(), List.of());
    }

    @Test
    void shouldDeleteQuiz() {
        //given
        Quiz quiz = new Quiz();
        quiz.setName("Special");
        Quiz savedQuiz = quizRepository.save(quiz);

        //when
        quizService.deleteQuiz(quiz.getId());

        //then
        assertTrue(quizRepository.findById(savedQuiz.getId()).isEmpty());
        assertThrows(EntityNotFoundException.class, () -> quizService.getQuiz(savedQuiz.getId()));
    }
}
