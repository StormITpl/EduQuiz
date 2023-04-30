package pl.stormit.eduquiz.quizcreator.domain.question;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionMapper;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestDto;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void shouldReturnQuestionByIdCorrectly() {
        // given
        UUID questionId = question.getId();

        // when
        QuestionDto foundQuestionDto = questionService.getQuestion(questionId);

        // then
        assertThat(foundQuestionDto.id()).isEqualTo(questionId);
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
    void shouldDeleteQuestionCorrectly() {
        // given
        Question savedQuestion = questionRepository.save(question);

        // when
        questionRepository.deleteById(savedQuestion.getId());

        // then
        assertThat(questionRepository.findById(question.getId())).isEmpty();
        assertThrows(EntityNotFoundException.class, () -> questionService.getQuestion(savedQuestion.getId()));
    }
}
