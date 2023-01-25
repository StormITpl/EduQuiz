package pl.stormit.eduquiz.quizcreator.quiz.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.category.domain.model.Category;

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

    public Quiz() {
        this.id = UUID.randomUUID();
    }

    public Quiz(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
