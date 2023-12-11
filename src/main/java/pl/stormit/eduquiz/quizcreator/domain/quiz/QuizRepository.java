package pl.stormit.eduquiz.quizcreator.domain.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    List<Quiz> getAllByCategoryId(UUID id);
    Page<Quiz> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Optional<Quiz> findByNameIgnoreCase(String name);
}
