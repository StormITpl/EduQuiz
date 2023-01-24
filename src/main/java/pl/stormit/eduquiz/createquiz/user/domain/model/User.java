package pl.stormit.eduquiz.createquiz.user.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    private UUID id;

    private String nickname;

    private String createQuiz;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String nickname, String createQuiz) {
        this();
        this.nickname = nickname;
        this.createQuiz = createQuiz;
    }
}
