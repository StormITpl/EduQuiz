package pl.stormit.eduquiz.game.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;

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

    @ElementCollection
    private List<Answer> userAnswers;

    @OneToOne
    private Quiz quiz;


}
