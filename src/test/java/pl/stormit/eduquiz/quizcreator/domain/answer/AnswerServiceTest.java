package pl.stormit.eduquiz.quizcreator.domain.answer;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerRequestMapper;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionService;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestMapper;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test"})
@SpringBootTest
class AnswerServiceTest {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerRequestMapper answerRequestMapper;

    @Autowired
    private QuestionRequestMapper questionRequestMapper;

    private Answer answer;

    private Question question;

    UUID questionId;

    UUID answerId;

    @BeforeEach
    void SetUp() {
        question = new Question();
        question.setContent("In which country was Nicolaus Copernicus born?");
        QuestionRequestDto questionRequestDto = questionRequestMapper.mapQuestionEntityToQuestionRequestDto(question);
        QuestionDto createdQuestionDto = questionService.createQuestion(questionRequestDto);
        questionId = createdQuestionDto.id();
        answer = new Answer();
        answer.setContent("Poland");
        answer.setCorrect(true);
        answer.setQuestion(question);
        AnswerRequestDto answerRequestDto = answerRequestMapper.mapAnswerEntityToAnswerRequestDto(answer);
        AnswerDto createdAnswerDto = answerService.createAnswer(createdQuestionDto.id(), answerRequestDto);
        answerId = createdAnswerDto.id();
    }

    @Test
    @Transactional
    void shouldCreateAnswerCorrectly() {
        // given
        QuestionRequestDto questionRequestDto = new QuestionRequestDto("In which country was Beethoven born?", null, null);
        QuestionDto createdQuestionDto = questionService.createQuestion(questionRequestDto);

        Question question = new Question();
        question.setId(createdQuestionDto.id());
        question.setContent("In which country was Beethoven born?");

        AnswerRequestDto answerRequestDto = AnswerRequestDto.builder()
                .content("Germany")
                .isCorrect(true)
                .question(question)
                .build();

        // when
        AnswerDto createdAnswerDto = answerService.createAnswer(createdQuestionDto.id(), answerRequestDto);

        // then
        assertNotNull(createdAnswerDto);
        assertNotNull(createdAnswerDto.id());
        assertEquals("Germany", createdAnswerDto.content());
        assertTrue(createdAnswerDto.isCorrect());
    }

    @Test
    @Transactional
    void shouldReturn404WhenCreatingAnswerForNonExistentQuestion() {
        // given
        UUID nonExistentQuestionId = UUID.randomUUID();
        AnswerRequestDto answerRequestDto = AnswerRequestDto.builder()
                .content("Germany")
                .isCorrect(true)
                .build();

        // when
        assertThrows(EntityNotFoundException.class, () -> {
            answerService.createAnswer(nonExistentQuestionId, answerRequestDto);
        });
    }

    @Test
    void shouldReturnListOfAnswersCorrectly() {
        // when
        List<AnswerDto> actualAnswers = answerService.getAnswers(questionId);

        // then
        assertNotNull(actualAnswers);
        assertThat(actualAnswers).hasSize(1);
        assertTrue(actualAnswers.get(0).isCorrect());
        assertEquals(actualAnswers.get(0).content(), "Poland");
        assertEquals(actualAnswers.get(0).question().getId(), questionId);
    }

    @Test
    void shouldReturnEmptyListWhenQuestionNotFound() {
        // given
        UUID nonExistentQuestionId = UUID.randomUUID();

        // when
        List<AnswerDto> answers = answerService.getAnswers(nonExistentQuestionId);

        // then
        assertNotNull(answers);
        assertTrue(answers.isEmpty());
    }

    @Test
    void shouldReturnAnswerByIdCorrectly() {
        // when
        AnswerDto foundAnswerDto = answerService.getAnswer(answerId);

        // then
        assertEquals(foundAnswerDto.content(), "Poland");
        assertTrue(foundAnswerDto.isCorrect());
        assertEquals(foundAnswerDto.question().getId(), questionId);
    }

    @Test
    void shouldReturnEntityNotFoundExceptionWhenAnswerNotFound() {
        // given
        UUID nonExistentAnswerId = UUID.randomUUID();

        // when and then
        assertThrows(EntityNotFoundException.class, () -> {
            answerService.getAnswer(nonExistentAnswerId);
        });
    }

    @Test
    void shouldUpdateAnswerCorrectly() {
        // given
        AnswerRequestDto answerRequestDto = new AnswerRequestDto("Spain", false, null);

        // when
        AnswerDto updatedAnswerDto = answerService.updateAnswer(answerId, answerRequestDto);

        // then
        assertThat(updatedAnswerDto.id()).isEqualTo(answerId);
        assertThat(updatedAnswerDto.content()).isEqualTo("Spain");
        assertThat(updatedAnswerDto.isCorrect()).isEqualTo(false);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenAnswerNotFound() {
        // given
        UUID nonExistentAnswerId = UUID.randomUUID();
        AnswerRequestDto answerRequestDto = new AnswerRequestDto("Spain", false, null);

        // when and then
        assertThrows(EntityNotFoundException.class, () -> {
            answerService.updateAnswer(nonExistentAnswerId, answerRequestDto);
        });
    }

    @Test
    void shouldDeleteAnswerCorrectly() {
        // when
        answerService.deleteAnswer(answerId);

        // then
        assertThrows(EntityNotFoundException.class, () -> answerService.getAnswer(answerId));
    }

    @Test
    void shouldThrowExceptionWhenAnswerNotFoundOnDeletion() {
        // given
        UUID nonExistentAnswerId = UUID.randomUUID();

        // when and then
        assertThrows(EntityNotFoundException.class, () -> answerService.deleteAnswer(nonExistentAnswerId));
    }
}
