package pl.stormit.eduquiz.quizcreator.domain.quiz;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
public class Quiz {

    @Id
    private UUID id;

    private String name;

    @JsonManagedReference
    @ManyToOne
    private Category category;

    @JsonManagedReference
    @ManyToOne
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    public Quiz() {
        this.id = UUID.randomUUID();
    }

    public Quiz(String name) {
        this();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Quiz quiz = (Quiz) o;
        return id != null && Objects.equals(id, quiz.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
