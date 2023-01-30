package pl.stormit.eduquiz.quizcreator.question.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {
    @Id
    private UUID id;
    private String content;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @ManyToOne
    private Quiz quiz;

    public Question() {
        this.id = UUID.randomUUID();
    }

    public Question(String name) {
        this.id = UUID.randomUUID();
        this.content = name;
    }
}
