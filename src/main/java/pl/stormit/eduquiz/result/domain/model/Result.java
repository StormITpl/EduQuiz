package pl.stormit.eduquiz.result.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.UUID;

@Entity
@Table(name = "results")
@Getter
@Setter
public class Result {

    @Id
    private UUID id;

    private Integer score;

    @OneToOne
    private Game game;

    @ManyToOne
    private Quiz quiz;
}
