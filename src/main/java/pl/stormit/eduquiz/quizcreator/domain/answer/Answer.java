package pl.stormit.eduquiz.quizcreator.domain.answer;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @NotBlank(message = "Content can't be empty")
    @Size(min = 10, max = 255, message = "Content must be between 10 and 255 characters")
    private String content;

    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;
}
