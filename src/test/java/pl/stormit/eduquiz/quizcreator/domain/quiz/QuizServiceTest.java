package pl.stormit.eduquiz.quizcreator.domain.quiz;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizCreationDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizEditingDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        Quiz firstQuiz = new Quiz("Royal");
        Quiz secondQuiz = new Quiz("Master");
        quizRepository.saveAll(List.of(firstQuiz, secondQuiz));
        //when
        List<Quiz> quizzes = quizService.getQuizzes();
        //then
        assertThat(quizzes)
                .hasSize(2)
                .extracting(Quiz::getName)
                .containsExactlyInAnyOrder("Royal", "Master");
    }

    @Test
    void shouldReturnOneQuizFoundById() {
        //given
        Quiz quiz = new Quiz("Special");
        quizRepository.save(quiz);
        //when
        Quiz quizFoundById = quizService.getQuiz(quiz.getId());
        //then
        assertEquals(quizFoundById, quiz);
    }

    @Test
    void shouldCreateQuiz() {
        //given
        Quiz quiz = new Quiz("Special");
        QuizCreationDto quizCreationDto = new QuizCreationDto(
                quiz.getName(), quiz.getCategory(), quiz.getUser(), quiz.getQuestions());
        //when
        QuizCreationDto createdQuiz = quizService.createQuiz(quizCreationDto);
        //then
        assertEquals(createdQuiz.name(), quiz.getName());
    }

    @Test
    void shouldUpdateQuiz() {
        //given
        Quiz quiz = new Quiz("Special", null, List.of());
        quizRepository.save(quiz);
        QuizEditingDto quizToUpdate = new QuizEditingDto("Pro", quiz.getCategory(), quiz.getQuestions());

        //when
        QuizEditingDto updatedQuiz = quizService.updateQuiz(quiz.getId(), quizToUpdate);
        //then
        assertEquals(updatedQuiz.name(), "Pro");
        assertNull(updatedQuiz.category());
        assertEquals(updatedQuiz.questions(), List.of());
    }

    @Test
    void shouldDeleteQuiz() {
        //given
        Quiz quiz = new Quiz("Special");
        quizRepository.save(quiz);
        //when
        quizService.deleteQuiz(quiz.getId());
        //then
        assertTrue(quizRepository.findById(quiz.getId()).isEmpty());
    }
}