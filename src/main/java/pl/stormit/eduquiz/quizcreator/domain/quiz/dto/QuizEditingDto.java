package pl.stormit.eduquiz.quizcreator.domain.quiz.dto;

import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

import java.util.List;

public record QuizEditingDto(String name, Category category, List<Question> questions) {
}