package pl.stormit.eduquiz.quizcreator.domain.question;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionMapper;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestions() {
        return questionRepository.findAll().stream()
                .map(questionMapper::mapQuestionEntityToQuestionDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public QuestionDto getQuestion(UUID questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new EntityNotFoundException("The question by id: " + questionId + ", does not exist.");
        });
        return questionMapper.mapQuestionEntityToQuestionDto(question);
    }

    @Transactional
    public QuestionDto createQuestion(QuestionRequestDto questionRequestDto) {

        Question question = new Question();
        question.setContent(questionRequestDto.content());
        question.setQuiz(questionRequestDto.quiz());
        question.setAnswers(questionRequestDto.answers());

        return questionMapper.mapQuestionEntityToQuestionDto(question);
    }

    @Transactional
    public QuestionDto updateQuestion(UUID questionId, QuestionRequestDto questionRequestDto) {

        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new EntityNotFoundException("The question by id: " + questionId + ", does not exist.");
        });
        question.setContent(questionRequestDto.content());
        question.setQuiz(questionRequestDto.quiz());
        question.setAnswers(questionRequestDto.answers());

        return questionMapper.mapQuestionEntityToQuestionDto(question);
    }

    @Transactional
    public void deleteQuestion(UUID questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
        } else {
            throw new EntityNotFoundException("The question by id: " + questionId + ", does not exist.");
        }
    }
}
