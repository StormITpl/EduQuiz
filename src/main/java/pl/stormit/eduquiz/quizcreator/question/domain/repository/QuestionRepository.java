package pl.stormit.eduquiz.quizcreator.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID>{

    public List<Question> findQuestionsByQuizId(UUID quizId);
}
