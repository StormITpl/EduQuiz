package pl.stormit.eduquiz.quizcreator.quiz.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;

import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
}
