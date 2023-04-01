package pl.stormit.eduquiz.quizcreator.domain.answer;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerMapper;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final AnswerMapper answerMapper;

    @Transactional
    public AnswerDto createAnswer(@NotNull AnswerDto answerRequest) {
        Answer answer = new Answer();
        answer.setContent(answerRequest.content());
        answer.setCorrect(answerRequest.isCorrect());
        return answerMapper.mapAnswerEntityToAnswerDto(answerRepository.save(answer));
    }

    @Transactional(readOnly = true)
    public List<AnswerDto> getAnswers(UUID questionId) {
        return answerRepository.findByQuestionId(questionId).stream()
                .map(answerMapper::mapAnswerEntityToAnswerDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Answer getAnswer(UUID id) {
        return answerRepository.getReferenceById(id);
    }

    @Transactional
    public AnswerDto updateAnswer(UUID answerId, AnswerDto answerRequest) {
        Answer answer = answerRepository.getReferenceById(answerId);
        answer.setContent(answerRequest.content());
        answer.setCorrect(answerRequest.isCorrect());
        return answerMapper.mapAnswerEntityToAnswerDto(answerRepository.save(answer));
    }

    @Transactional
    public void deleteAnswer(UUID answerId) {
        answerRepository.deleteById(answerId);
    }
}
