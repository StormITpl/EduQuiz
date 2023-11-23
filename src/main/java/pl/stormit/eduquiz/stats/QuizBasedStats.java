package pl.stormit.eduquiz.stats;


import lombok.Getter;
import lombok.Setter;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class QuizBasedStats {
    String quizName;
    User quizUser;
    LocalDateTime quizStartTime;
    LocalDateTime quizEndTime;
    LocalTime quizDuration;
}


