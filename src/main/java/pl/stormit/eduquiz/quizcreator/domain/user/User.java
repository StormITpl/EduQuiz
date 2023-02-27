package pl.stormit.eduquiz.quizcreator.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    private UUID id;

    private String nickname;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Quiz> quizzes;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String nickname) {
        this();
        this.nickname = nickname;
    }
}
