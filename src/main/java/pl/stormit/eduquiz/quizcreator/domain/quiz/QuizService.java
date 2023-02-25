package pl.stormit.eduquiz.quizcreator.domain.quiz;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizCreationDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizCreationMapper;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizEditingDto;
import pl.stormit.eduquiz.quizcreator.domain.quiz.dto.QuizEditingMapper;

import java.util.List;
import java.util.UUID;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuizCreationMapper quizCreationMapper;
    private final QuizEditingMapper quizEditingMapper;

    public QuizService(QuizRepository quizRepository, QuizCreationMapper quizCreationMapper, QuizEditingMapper quizEditingMapper) {
        this.quizRepository = quizRepository;
        this.quizCreationMapper = quizCreationMapper;
        this.quizEditingMapper = quizEditingMapper;
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
    public QuizCreationDto createQuiz(@NotNull QuizCreationDto quizRequest) {
        Quiz quiz = new Quiz(quizRequest.name(), quizRequest.category(), quizRequest.user(), quizRequest.questions());
        return quizCreationMapper.mapQuizEntityToQuizCreationDto(quizRepository.save(quiz));
    }

    @Transactional
    public QuizEditingDto updateQuiz(UUID id, @NotNull QuizEditingDto quizRequest) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow();
        quiz.setName(quizRequest.name());
        quiz.setCategory(quizRequest.category());
        quiz.setQuestions(quizRequest.questions());
        return quizEditingMapper.mapQuizEntityQuizEditingDto(quizRepository.save(quiz));
    }

    @Transactional
    public void deleteQuiz(UUID id) {
        quizRepository.deleteById(id);
    }
}
