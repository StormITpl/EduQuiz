package pl.stormit.eduquiz.quizcreator.domain.question;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Question content must not be blank")
    @Size(min = 3, max = 255, message = "Question content must be 3 to 255 characters")
    private String content;

    private String correctAnswer;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> answers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

}
