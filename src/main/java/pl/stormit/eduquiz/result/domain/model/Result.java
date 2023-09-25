package pl.stormit.eduquiz.result.domain.model;

import jakarta.persistence.*;
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
