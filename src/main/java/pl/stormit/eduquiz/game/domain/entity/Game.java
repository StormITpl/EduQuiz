package pl.stormit.eduquiz.game.domain.entity;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "games")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue
    private UUID id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<UUID> userAnswers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    public Game(Quiz quiz) {
        this.quiz = quiz;
    }
}
