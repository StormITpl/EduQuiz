package pl.stormit.eduquiz.quizcreator.answer.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;

import java.util.UUID;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer {
    @Id
    private UUID id;
    private String name;

    boolean isCorrect;
    @ManyToOne
    private Question question;

    public Answer() {
        this.id = UUID.randomUUID();
    }

    public Answer(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
}

