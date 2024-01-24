package pl.stormit.eduquiz.quizcreator.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    List<Quiz> getAllByCategoryId(UUID id);

    @Query("select q from Quiz q order by q.createdAt desc limit 3")
    List<Quiz> getThreeNewest();
}
