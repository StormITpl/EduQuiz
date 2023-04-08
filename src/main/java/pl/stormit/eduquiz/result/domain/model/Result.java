package pl.stormit.eduquiz.result.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import pl.stormit.eduquiz.game.domain.entity.Game;

import java.util.UUID;

@Entity
@Table(name = "results")
@Data
public class Result {

    @Id
    @GeneratedValue
    private UUID id;

    private Integer score;

    @OneToOne
    private Game game;
}
