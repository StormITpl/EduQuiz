package pl.stormit.eduquiz.statistic.quizstatistic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.stormit.eduquiz.game.domain.entity.Game;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz_statistics")
class QuizStatistic {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "game_id", unique = true)
    private Game game;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "score")
    private int score;

    @Column(name = "duration")
    private long duration;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
