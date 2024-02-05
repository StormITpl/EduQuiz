package pl.stormit.eduquiz.statistic.quizstatistic;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz_statistics")
public class QuizStatistic {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    Game game;

    @OneToOne
    User user;

    int score;

    long duration;

}
