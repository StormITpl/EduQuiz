package pl.stormit.eduquiz.quizcreator.domain.question;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Content can't be empty")
    @Size(min = 5, max = 255, message = "Content must be between 5 and 255 characters")
    private String content;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    @NotEmpty
    private List<Answer> answers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;
}
