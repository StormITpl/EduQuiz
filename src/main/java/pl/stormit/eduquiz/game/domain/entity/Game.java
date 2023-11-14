package pl.stormit.eduquiz.game.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "games")
@Data
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_at")
    private LocalDateTime start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finish_at")
    private LocalDateTime finish;

    @Column(name ="duration")
    private long duration;

    public void setStart() {
        this.start = LocalDateTime.now();
    }

    public void setFinish() {
        this.finish = LocalDateTime.now();
    }
}
