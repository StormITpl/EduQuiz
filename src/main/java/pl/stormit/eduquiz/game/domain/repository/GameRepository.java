package pl.stormit.eduquiz.game.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    List<Answer> findAllByGameId(UUID id);

    @Query(value = "SELECT * FROM User u")
    Optional<Game> findByIdUserAnswers(UUID uuid);
}
