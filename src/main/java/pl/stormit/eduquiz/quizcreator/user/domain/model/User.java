package pl.stormit.eduquiz.quizcreator.user.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    private UUID id;

    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Quiz> quizzes;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String nickname) {
        this();
        this.nickname = nickname;
    }
}
