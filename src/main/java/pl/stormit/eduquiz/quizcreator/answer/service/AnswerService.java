package pl.stormit.eduquiz.quizcreator.answer.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.answer.domain.repository.AnswerRepository;
import pl.stormit.eduquiz.quizcreator.question.domain.repository.QuestionRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional(readOnly = true)
    public List<Answer> getAnswers(UUID questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @Transactional(readOnly = true)
    public Answer getAnswer(UUID id) {
        return answerRepository.getById(id);
    }

    @Transactional
    public Answer updateAnswer(UUID answerId, Answer answerRequest) {
        Answer answer = answerRepository.getById(answerId);
        answer.setName(answerRequest.getName());

        return answerRepository.save(answer);
    }

    @Transactional
    public void deleteAnswer(UUID answerId) {
        answerRepository.deleteById(answerId);
    }
}
