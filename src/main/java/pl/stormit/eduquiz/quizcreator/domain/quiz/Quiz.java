package pl.stormit.eduquiz.quizcreator.domain.quiz;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    @GeneratedValue
    private UUID id;

    private String name;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<Question> questions;

    public Quiz() {
        this.id = UUID.randomUUID();
    }

    public Quiz(String name) {
        this();
        this.name = name;
    }

    public Quiz(String name, Category category, List<Question> questions) {
        this.name = name;
        this.category = category;
        this.questions = questions;
    }

    public Quiz(String name, Category category, User user, List<Question> questions) {
        this.name = name;
        this.category = category;
        this.user = user;
        this.questions = questions;
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
