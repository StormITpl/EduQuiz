package pl.stormit.eduquiz.quizcreator.domain.question;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

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

    @JsonBackReference
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @JsonManagedReference
    @ManyToOne
    private Quiz quiz;

    public Question() {
        this.id = UUID.randomUUID();
    }

    public Question(String name) {
        this();
        this.content = name;
    }
}
