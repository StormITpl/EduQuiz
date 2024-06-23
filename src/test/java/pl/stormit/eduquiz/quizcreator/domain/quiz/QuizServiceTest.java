package pl.stormit.eduquiz.quizcreator.domain.quiz;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestMapper;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private QuizRepository quizRepository;

    @Test
    void shouldReturnListOfQuizzesCorrectly() {
        // given
        Quiz firstQuiz = new Quiz();
        firstQuiz.setName("Data Structure");
        Quiz secondQuiz = new Quiz();
        secondQuiz.setName("General knowledge of world history");
        quizRepository.saveAll(List.of(firstQuiz, secondQuiz));

        // when
        List<QuizDto> quizzesDto = quizService.getQuizzes();

        // then
        assertThat(quizzesDto).isNotNull();
        assertThat(quizzesDto).hasSize(2);
        assertThat(quizzesDto).extracting(QuizDto::name).containsExactlyInAnyOrder("Data Structure", "General knowledge of world history");
    }

    @Test
    void shouldNotReturnQuizzes() {
        // when
        List<QuizDto> quizzesDto = quizService.getQuizzes();

        // then
        assertThat(quizzesDto).isEmpty();
    }

    @Test
    void shouldReturnOneQuizFoundById() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Security");
        QuizRequestDto quizRequestDto = quizRequestMapper.mapQuizEntityToQuizRequestDto(quiz);
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);


        // when
        QuizDto quizDtoFoundById = quizService.getQuiz(createdQuiz.id());

        // then
        assertEquals(quizDtoFoundById.id(), createdQuiz.id());
        assertEquals(quizDtoFoundById.name(), createdQuiz.name());
    }

    @Test
    void shouldNotReturnQuizByIdWhenNotFound() {
        // given
        UUID nonExistentQuizId = UUID.randomUUID();

        // when and then
        assertThrows(EntityNotFoundException.class, () -> quizService.getQuiz(nonExistentQuizId));
    }

    @Test
    void shouldCreateQuiz() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Security");
        QuizRequestDto quizRequestDto = new QuizRequestDto(
                quiz.getName(),
                quiz.getCategory(),
                quiz.getUser(),
                quiz.getQuestions());

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
        quiz.setName("Security");
        QuizRequestDto quizRequestDto = quizRequestMapper.mapQuizEntityToQuizRequestDto(quiz);
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);
        QuizRequestDto quizToUpdate = new QuizRequestDto("Security-Pro",
                quiz.getCategory(),
                quiz.getUser(),
                quiz.getQuestions());

        // when
        QuizDto updatedQuiz = quizService.updateQuiz(createdQuiz.id(), quizToUpdate);

        // then
        assertEquals(updatedQuiz.name(), "Security-Pro");
        assertNull(updatedQuiz.category());
        assertNull(updatedQuiz.questions());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentQuiz() {
        // given
        UUID nonExistentQuizId = UUID.randomUUID();
        QuizRequestDto quizRequestDto = new QuizRequestDto(
                "NonExistentQuiz",
                new Category(),
                new User(),
                Collections.emptyList()
        );

        // when and then
        assertThrows(EntityNotFoundException.class, () -> {
            quizService.updateQuiz(nonExistentQuizId, quizRequestDto);
        });
    }

    @Test
    void shouldDeleteQuiz() {
        // given
        Quiz quiz = new Quiz();
        quiz.setName("Security");
        QuizRequestDto quizRequestDto = quizRequestMapper.mapQuizEntityToQuizRequestDto(quiz);
        QuizDto createdQuiz = quizService.createQuiz(quizRequestDto);

        // when
        quizService.deleteQuiz(createdQuiz.id());

        // then
        assertThrows(EntityNotFoundException.class, () -> quizService.getQuiz(createdQuiz.id()));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentQuiz() {
        // given
        UUID nonExistentQuizId = UUID.randomUUID();

        // when and then
        assertThrows(EntityNotFoundException.class, () -> {
            quizService.deleteQuiz(nonExistentQuizId);
        });
    }
}
