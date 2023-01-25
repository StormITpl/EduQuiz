package pl.stormit.eduquiz.quizcreator.question.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.category.domain.model.Category;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {
    @Id
    private UUID id;
    private String name;

    @Column(nullable = false)
    @ColumnDefault("4")
    private int amountOfCorrectAnswers;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @ManyToOne
    private Category category;

    public Question() {
        this.id = UUID.randomUUID();
    }

    public Question(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}
