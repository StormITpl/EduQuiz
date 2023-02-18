package pl.stormit.eduquiz.quizcreator.domain.answer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

import java.util.UUID;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer {

    @Id
    private UUID id;

    private String content;

    private boolean isCorrect;

    @JsonManagedReference
    @ManyToOne
    private Question question;

    public Answer() {
        this.id = UUID.randomUUID();
    }

    public Answer(String name, boolean isCorrect) {
        this();
        this.content = name;
        this.isCorrect = isCorrect;
    }
}
