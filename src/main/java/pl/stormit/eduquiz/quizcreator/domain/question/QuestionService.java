package pl.stormit.eduquiz.quizcreator.domain.question;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionDto;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionMapper;
import pl.stormit.eduquiz.quizcreator.domain.question.dto.QuestionRequestDto;

import java.util.List;
import java.util.UUID;

@Validated
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
    public QuestionDto getQuestion(@NotNull @PathVariable("question-id") UUID questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new EntityNotFoundException("The question by id: " + questionId + ", does not exist.");
        });
        return questionMapper.mapQuestionEntityToQuestionDto(question);
    }

    @Transactional
    public QuestionDto createQuestion(@Valid @RequestBody QuestionRequestDto questionRequestDto) {
        Question question = new Question();
        question.setContent(questionRequestDto.content());
        question.setQuiz(questionRequestDto.quiz());
        question.setAnswers(questionRequestDto.answers());
        question.setCorrectAnswer(questionRequestDto.correctAnswer());
        questionRepository.save(question);
        QuestionDto questionDto = new QuestionDto(question.getId(), question.getContent(), question.getCorrectAnswer(), question.getQuiz(), question.getAnswers());
        return questionDto;
    }

    @Transactional
    public QuestionDto updateQuestion(@NotNull @PathVariable("question-id") UUID questionId,
                                      @Valid @RequestBody QuestionRequestDto questionRequestDto) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> {
            throw new EntityNotFoundException("The question by id: " + questionId + ", does not exist.");
        });
        question.setContent(questionRequestDto.content());
        question.setQuiz(questionRequestDto.quiz());
        question.setAnswers(questionRequestDto.answers());
        question.setCorrectAnswer(questionRequestDto.correctAnswer());
        question=questionRepository.save(question);
        QuestionDto questionDto = new QuestionDto(question.getId(), question.getContent(), question.getCorrectAnswer(), question.getQuiz(), question.getAnswers());
        return questionDto;
    }

    @Transactional
    public void deleteQuestion(@NotNull @PathVariable("question-id") UUID questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
        } else {
            throw new EntityNotFoundException("The question by id: " + questionId + ", does not exist.");
        }
    }
}