package pl.stormit.eduquiz.quizcreator.domain.question;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionMapper;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    private Question question;

    @BeforeEach
    void SetUp() {
        question = new Question();
        question.setContent("In which country was Nicolaus Copernicus born?");

        List<Answer> answers = new ArrayList<>();

        Answer firstAnswer = Answer.builder()
                .content("in Torun")
                .isCorrect(true)
                .question(question)
                .build();

        Answer secondAnswer = Answer.builder()
                .content("in Cracow")
                .isCorrect(false)
                .question(question)
                .build();

        answers.add(firstAnswer);
        answers.add(secondAnswer);

        question.setAnswers(answers);
        questionRepository.save(question);
    }

    @Test
    void shouldCreateQuestionCorrectly() {
        // given
        QuestionRequestDto questionRequestDto = new QuestionRequestDto("In which country was Nicolaus Copernicus born?", null, null);

        // when
        QuestionDto createdQuestionDto = questionService.createQuestion(questionRequestDto);

        // then
        assertThat(createdQuestionDto.content()).isEqualTo(questionRequestDto.content());
        assertThat(createdQuestionDto.answers()).isNull();
        assertThat(createdQuestionDto.quiz()).isNull();
    }

    @Test
    void shouldNotCreateQuestionWithEmptyContent() {
        // given
        QuestionRequestDto questionRequestDto = new QuestionRequestDto(null, null, null);

        // when
        QuestionDto createdQuestionDto = questionService.createQuestion(questionRequestDto);

        // then
        assertNotNull(createdQuestionDto);
        assertNull(createdQuestionDto.content());
        assertNull(createdQuestionDto.quiz());
        assertNull(createdQuestionDto.answers());
    }

    @Test
    void shouldReturnListOfQuestionsCorrectly() {
        // given
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> expectedQuestions = questionMapper.mapQuestionEntityToQuestionDtoList(questions);

        // when
        List<QuestionDto> actualQuestions = questionService.getQuestions();

        // then
        assertThat(actualQuestions).isNotNull();
        assertThat(actualQuestions).hasSize(expectedQuestions.size());
        assertThat(actualQuestions).containsExactlyElementsOf(expectedQuestions);
    }

    @Test
    void shouldNotReturnListOfQuestionsCorrectly() {
        // given
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> expectedQuestions = questionMapper.mapQuestionEntityToQuestionDtoList(questions);

        List<QuestionDto> actualQuestions = new ArrayList<>(expectedQuestions);

        QuestionDto additionalQuestion = new QuestionDto(UUID.randomUUID(), "What was the name of the First Historical Era?", null, null);
        actualQuestions.add(additionalQuestion);

        // then
        assertThat(actualQuestions).isNotNull();
        assertThat(actualQuestions).hasSize(expectedQuestions.size() + 1);
        assertThat(actualQuestions).isNotEqualTo(expectedQuestions);
    }

    @Test
    void shouldReturnQuestionByIdCorrectly() {
        // given
        UUID questionId = question.getId();

        // when
        QuestionDto foundQuestionDto = questionService.getQuestion(questionId);

        // then
        assertThat(foundQuestionDto.id()).isEqualTo(questionId);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenQuestionNotFound() {
        // given
        UUID nonExistentQuestionId = UUID.randomUUID();

        // when and then
        assertThrows(EntityNotFoundException.class, () -> {
            questionService.getQuestion(nonExistentQuestionId);
        });
    }

    @Test
    void shouldUpdateQuestionCorrectly() {
        // given
        UUID questionId = question.getId();
        QuestionRequestDto questionRequestDto = new QuestionRequestDto("What was the name of the First Historical Era?", null, null);

        // when
        QuestionDto updatedQuestionDto = questionService.updateQuestion(questionId, questionRequestDto);

        // then
        assertThat(updatedQuestionDto.id()).isEqualTo(questionId);
        assertThat(updatedQuestionDto.content()).isEqualTo("What was the name of the First Historical Era?");
        assertThat(updatedQuestionDto.quiz()).isEqualTo(null);
        assertThat(updatedQuestionDto.answers()).isEqualTo(null);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentQuestion() {
        // given
        UUID nonExistentQuestionId = UUID.randomUUID();
        QuestionRequestDto questionRequestDto = new QuestionRequestDto("What was the name of the First Historical Era?", null, null);

        // when and then
        assertThrows(EntityNotFoundException.class, () -> {
            questionService.updateQuestion(nonExistentQuestionId, questionRequestDto);
        });
    }

    @Test
    void shouldDeleteQuestionCorrectly() {
        // given
        Question savedQuestion = questionRepository.save(question);

        // when
        questionRepository.deleteById(savedQuestion.getId());

        // then
        assertThat(questionRepository.findById(question.getId())).isEmpty();
        assertThrows(EntityNotFoundException.class, () -> questionService.getQuestion(savedQuestion.getId()));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentQuestion() {
        // given
        UUID nonExistentQuestionId = UUID.randomUUID();

        // when and then
        assertThrows(EntityNotFoundException.class, () -> {
            questionService.deleteQuestion(nonExistentQuestionId);
        });
    }
}
