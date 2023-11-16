package pl.stormit.eduquiz.game.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.eduquiz.game.domain.entity.Game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {

    Optional<Game> findTopByQuiz_IdOrderByDurationAsc(UUID quizId);

    Long countAllByQuiz_Id(UUID quizId);

    List<Game> findGameByStartBetween(LocalDateTime start, LocalDateTime finish);

}
