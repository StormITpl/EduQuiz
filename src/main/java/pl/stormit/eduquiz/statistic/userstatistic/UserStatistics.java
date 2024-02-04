package pl.stormit.eduquiz.statistic.userstatistic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_statistics")
@Data
@AllArgsConstructor
@NoArgsConstructor
class UserStatistics {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "last_login")
    private Instant lastLogin;

    @Column(name = "login_count", nullable = false)
    private int loginCount;

    @Column(name = "created_quizzes_count", nullable = false)
    private int createdQuizzesCount;

    @Column(name = "solved_quiz_count", nullable = false)
    private int solvedQuizCount;
}
