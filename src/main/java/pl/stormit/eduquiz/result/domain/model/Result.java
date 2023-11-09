package pl.stormit.eduquiz.result.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.stormit.eduquiz.game.domain.entity.Game;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "results")
@Data
public class Result {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private Integer score;

    @OneToOne
    private Game game;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_at")
    private Instant start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finish_at")
    private Instant finish;
}
