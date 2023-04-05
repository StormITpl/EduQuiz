package pl.stormit.eduquiz.quizcreator.domain.answer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerMapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerRequestDto;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final AnswerMapper answerMapper;

    @Transactional
    public AnswerDto createAnswer(UUID questionId, AnswerRequestDto answerRequestDto) {

        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new EntityNotFoundException("The question by id: " + questionId + ", does not exist.");
        });
        Answer answer = new Answer();
        answer.setContent(answerRequestDto.content());
        answer.setCorrect(answerRequestDto.isCorrect());
        answer.setQuestion(question);

        return answerMapper.mapAnswerEntityToAnswerDto(answer);
    }

    @Transactional(readOnly = true)
    public List<AnswerDto> getAnswers(UUID questionId) {
        return answerRepository.findByQuestionId(questionId).stream()
                .map(answerMapper::mapAnswerEntityToAnswerDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AnswerDto getAnswer(UUID answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> {
            throw new EntityNotFoundException("The answer by id: " + answerId + ", does not exist.");
        });
        return answerMapper.mapAnswerEntityToAnswerDto(answer);
    }

    @Transactional
    public AnswerDto updateAnswer(UUID answerId, AnswerRequestDto answerRequestDto) {

        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> {
            throw new EntityNotFoundException("The answer by id: " + answerId + ", does not exist.");
        });
        answer.setContent(answerRequestDto.content());
        answer.setCorrect(answerRequestDto.isCorrect());

        return answerMapper.mapAnswerEntityToAnswerDto(answer);
    }

    @Transactional
    public void deleteAnswer(UUID answerId) {
        if (answerRepository.existsById(answerId)) {
            answerRepository.deleteById(answerId);
        } else {
            throw new EntityNotFoundException("The answer by id: " + answerId + ", does not exist.");
        }
    }
}
