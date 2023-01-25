package pl.stormit.eduquiz.quizcreator.quiz.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;
import pl.stormit.eduquiz.quizcreator.quiz.domain.repository.QuizRepository;

import java.util.List;
import java.util.UUID;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Transactional(readOnly = true)
    public List<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Quiz getQuiz(UUID id) {
        return quizRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public Quiz createQuiz(@NotNull Quiz quizRequest) {
        Quiz quiz = new Quiz();
        quiz.setName(quizRequest.getName());
        quiz.setCategory(quizRequest.getCategory());
        return quizRepository.save(quiz);
    }

    @Transactional
    public Quiz updateQuiz(UUID id, @NotNull Quiz quizRequest) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow();
        quiz.setName(quizRequest.getName());
        quiz.setCategory(quizRequest.getCategory());
        return quizRepository.save(quiz);
    }

    @Transactional
    public void deleteQuiz(UUID id) {
        quizRepository.deleteById(id);
    }
}
