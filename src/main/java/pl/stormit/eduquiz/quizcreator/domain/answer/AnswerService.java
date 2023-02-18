package pl.stormit.eduquiz.quizcreator.domain.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.question.QuestionRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<Answer> getAnswers(UUID questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @Transactional(readOnly = true)
    public Answer getAnswer(UUID id) {
        return answerRepository.getReferenceById(id);
    }

    @Transactional
    public Answer updateAnswer(UUID answerId, Answer answerRequest) {
        Answer answer = answerRepository.getReferenceById(answerId);
        answer.setContent(answerRequest.getContent());
        answer.setCorrect(answerRequest.isCorrect());
        return answerRepository.save(answer);
    }

    @Transactional
    public void deleteAnswer(UUID answerId) {
        answerRepository.deleteById(answerId);
    }
}
