package pl.stormit.eduquiz.quizcreator.domain.quiz;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.user.Status;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Quiz name must not be blank")
    @Size(min = 3, max = 255, message = "Quiz name must be 3 to 255 characters")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<Game> games;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();
}
