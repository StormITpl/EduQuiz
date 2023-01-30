package pl.stormit.eduquiz.quizcreator.quiz.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.category.domain.model.Category;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import pl.stormit.eduquiz.quizcreator.user.domain.model.User;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
public class Quiz {
    @Id
    private UUID id;
    private String name;
    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "quiz")
    private List<Question> question;

    public Quiz() {
        this.id = UUID.randomUUID();
    }

    public Quiz(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
