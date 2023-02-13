package pl.stormit.eduquiz.quizcreator.quiz.dto;

import pl.stormit.eduquiz.quizcreator.category.domain.model.Category;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;
import pl.stormit.eduquiz.quizcreator.user.domain.model.User;

import java.util.List;


public record QuizDto(
        String name,
        Category category,
        User user,
        List<Question> question

){}
