package pl.stormit.eduquiz.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.answer.domain.repository.AnswerRepository;
import pl.stormit.eduquiz.quizcreator.category.domain.model.Category;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import pl.stormit.eduquiz.quizcreator.question.domain.repository.QuestionRepository;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;
import pl.stormit.eduquiz.quizcreator.quiz.domain.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final QuizRepository quizRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    public Quiz chooseQuiz(Quiz quiz) {
        return quizRepository.findById(quiz.getId()).orElseThrow(() -> {
            throw new MissingQuizException("The quiz by id does not exist.");;
        });
//        List<Question> questionsList = chosenQuiz.getQuestion();
    }

    public Game newUserGame(){

        List<Answer> userAnswers = userAnswer();
        Game game = new Game();
        game.setQuiz(quiz);
        game.setUserAnswers(userAnswers);

        return null;
    }

    public List<Answer> userAnswer() {

        return null;
    }

    public List<Question> findAllQuizQuestions(UUID id) {

        return questionRepository.findQuestionsByQuizId(id);
    }

    public String showQuestionAndAnswers(Question questionRequest){
        Question question = singleQuestion(questionRequest.getId());
        List<Answer> answers = findAllAnswersForQuestion(questionRequest.getId());

        return question.getContent();
    }



    public Question singleQuestion(UUID id){
        return null;
    }

    public List<Answer> findAllAnswersForQuestion(UUID id) {

        return answerRepository.findByQuestionId(id);
    }

    List<Answer> answers = new ArrayList<>();

    public List<Answer> addUserAnswer(Answer answerRequest) {
        answers.add(answerRequest);
        return answers;
    }
}
