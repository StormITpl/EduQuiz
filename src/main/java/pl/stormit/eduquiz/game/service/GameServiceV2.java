package pl.stormit.eduquiz.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.answer.domain.repository.AnswerRepository;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import pl.stormit.eduquiz.quizcreator.question.domain.repository.QuestionRepository;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;
import pl.stormit.eduquiz.quizcreator.quiz.domain.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GameServiceV2 {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private List<Answer> userAnswers;

    @Autowired
    public GameServiceV2(QuizRepository quizRepository,
                         QuestionRepository questionRepository,
                         AnswerRepository answerRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userAnswers = new ArrayList<>();
    }

    public Quiz chooseQuiz(UUID quizId) {
        return quizRepository.findById(quizId).orElseThrow(() ->
                new MissingQuizException("The quiz with id " + quizId + " does not exist."));
    }

    public Game newUserGame(UUID quizId) {
        Quiz quiz = chooseQuiz(quizId);
        Game game = new Game();
        game.setQuiz(quiz);
        game.setUserAnswers(userAnswers);
        return game;
    }

    public List<Question> findAllQuizQuestions(UUID quizId) {
        return questionRepository.findQuestionsByQuizId(quizId);
    }

    public Question findQuestionById(UUID questionId) {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new MissingQuestionException("The question with id " + questionId + " does not exist."));
    }

    public List<Answer> findAllAnswersForQuestion(UUID questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public void addUserAnswer(Answer answer) {
        userAnswers.add(answer);
    }
}