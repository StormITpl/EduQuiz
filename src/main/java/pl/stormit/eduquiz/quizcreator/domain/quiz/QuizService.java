package pl.stormit.eduquiz.quizcreator.domain.quiz;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizDtoMapper;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizRequestDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    private final QuizDtoMapper quizMapper;

    @Transactional(readOnly = true)
    public List<QuizDto> getQuizzes() {
        List<Quiz> foundQuizzes = quizRepository.findAll();
        return quizMapper.mapQuizListOfEntityToQuizDtoList(foundQuizzes);
    }

    @Transactional(readOnly = true)
    public QuizDto getQuiz(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> {
            throw new EntityNotFoundException("Quiz by id: " + quizId + " does not exist.");
        });
        return quizMapper.mapQuizEntityToQuizDto(quiz);
    }

    @Transactional
    public QuizDto createQuiz(@NotNull QuizRequestDto quizRequest) {
        Quiz quiz = new Quiz();
        quiz.setName(quizRequest.name());
        quiz.setCategory(quizRequest.category());
        quiz.setUser(quizRequest.user());
        quiz.setQuestions(quizRequest.questions());
        return quizMapper.mapQuizEntityToQuizDto(quizRepository.save(quiz));
    }

    @Transactional
    public QuizDto updateQuiz(@NotNull UUID quizId,  @NotNull QuizRequestDto quizRequest) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> {
            throw new EntityNotFoundException("Quiz by id: " + quizId + " does not exist.");
        });
        quiz.setName(quizRequest.name());
        quiz.setCategory(quizRequest.category());
        quiz.setUser(quizRequest.user());
        quiz.setQuestions(quizRequest.questions());
        Quiz savedQuiz = quizRepository.save(quiz);
        return quizMapper.mapQuizEntityToQuizDto(savedQuiz);
    }

    @Transactional
    public void deleteQuiz(@NotNull UUID quizId) {
        if (quizRepository.existsById(quizId)) {
            quizRepository.deleteById(quizId);
        } else {
            throw new EntityNotFoundException("Quiz by id: " + quizId + " does not exist.");
        }
    }
}
