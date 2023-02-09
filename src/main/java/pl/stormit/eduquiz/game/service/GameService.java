package pl.stormit.eduquiz.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.answer.domain.repository.AnswerRepository;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import pl.stormit.eduquiz.quizcreator.question.domain.repository.QuestionRepository;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;
import pl.stormit.eduquiz.quizcreator.quiz.domain.repository.QuizRepository;
import pl.stormit.eduquiz.quizcreator.quiz.dto.QuizDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private List<String> userAnswers = new ArrayList<>();

    public QuizDto chooseQuiz(QuizDto quizRequest) {
        return quizRepository.findById(quizRequest.getId()).orElseThrow(() -> {
            throw new RuntimeException("The quiz by id does not exist.");
        });
    }

    public Game createGame(QuizDto quizRequest) {
        QuizDto selectedQuiz = chooseQuiz(quizRequest);
        Game game = new Game();
        game.setQuiz(selectedQuiz);
        game.setUserAnswers(userAnswers);
        return game;
    }

    public List<Question> findAllQuizQuestions(UUID quizId) {

        return questionRepository.findQuestionsByQuizId(quizId);
    }

    public String showQuestionAndAnswers(Question questionRequest) {
        Question question = findQuestionById(questionRequest.getId());
        List<Answer> allAnswers = findAllAnswersForQuestion(questionRequest.getId());
        return question.getContent();
    }

    public Question findQuestionById(UUID questionId) {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new RuntimeException("The question with id " + questionId + " does not exist."));
    }

    public List<Answer> findAllAnswersForQuestion(UUID questionId) {

        return answerRepository.findByQuestionId(questionId);
    }

    public List<Answer> addUserAnswer(Answer answerRequest) {
        userAnswers.add(answerRequest);
        return userAnswers;
    }
}
