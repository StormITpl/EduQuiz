package pl.stormit.eduquiz.quizcreator.domain.answer;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

import java.util.UUID;

@Entity
@Builder
@Table(name = "answers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Answer content must not be blank")
    @Size(min = 1, max = 255, message = "Answer content must be 1 to 255 characters")
    private String content;

    //private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;
}
