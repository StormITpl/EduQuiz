package pl.stormit.eduquiz.quizcreator.domain.answer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerMapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerRequestDto;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
public class AnswerServiceTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerMapper answerMapper;

    private Answer answer;

    @BeforeEach
    void SetUp(){
        answer = new Answer();
        answer.setContent("Poland");
        answer.setCorrect(true);
        answer.setQuestion(null);
    }

    @Test
    void shouldCreateAnswerCorrectly() {
        //given
        AnswerRequestDto userRequestDto = new AnswerRequestDto("Poland", true, null);

        //when
        AnswerDto createdAnswerDto = answerService.createAnswer(userRequestDto);

        //then
        assertThat(createdAnswerDto.content()).isEqualTo(userRequestDto.content());
        assertThat(createdAnswerDto.isCorrect()).isEqualTo(userRequestDto.isCorrect());
        assertThat(createdAnswerDto.question()).isNull();
    }

    @Test
    void shouldReturnListOfAnswersCorrectly() {
        //given
        List<Answer> answers = answerRepository.findAll();
        List<AnswerDto> expectedAnswers = answerMapper.mapAnswerEntityToAnswerDto(answers);

        //when
        List<AnswerDto> actualAnswers = answerService.getAnswers(null);

        //then
        assertThat(actualAnswers).isNotNull();
        assertThat(actualAnswers).hasSize(expectedAnswers.size());
        assertThat(actualAnswers).containsExactlyElementsOf(expectedAnswers);
    }

    @Test
    void shouldReturnAnswerByIdCorrectly() {
        //given
        UUID answerId = answer.getId();

        //when
        AnswerDto foundAnswerDto = answerService.getAnswer(answerId);

        //then
        assertThat(foundAnswerDto.id()).isEqualTo(answerId);
    }

    @Test
    void shouldUpdateAnswerCorrectly() {
        //given
        UUID answerId = answer.getId();
        AnswerRequestDto answerRequestDto = new AnswerRequestDto("Spain", false, null)

        //when
        AnswerDto updatedAnswerDto = answerService.updateAnswer(answerId, answerRequestDto);

        //then
        assertThat(updatedAnswerDto.id()).isEqualTo(answerId);
        assertThat(updatedAnswerDto.content()).isEqualTo("Spain");
        assertThat(updatedAnswerDto.isCorrect()).isEqualTo(false);
    }

    @Test
    void shouldDeleteAnswerCorrectly() {
        //given
        Answer savedAnswer = answerRepository.save(answer);

        //when
        answerRepository.deleteById(savedAnswer.getId());

        //then
        assertThat(answerRepository.findById(answer.getId())).isEmpty();
        assertThrows(EntityNotFoundException.class, () -> answerService.getAnswer(savedAnswer.getId()));
    }
}
