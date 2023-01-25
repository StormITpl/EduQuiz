package pl.stormit.eduquiz.game.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.eduquiz.game.domain.entity.Game;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<UUID, Game> {
}
