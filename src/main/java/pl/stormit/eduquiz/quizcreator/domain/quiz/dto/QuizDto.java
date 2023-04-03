package pl.stormit.eduquiz.quizcreator.domain.quiz.dto;

import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.quizcreator.domain.category.Category;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.util.List;
import java.util.UUID;

public record QuizDto(UUID id, String name, Category category, User user,
                      List<Question> questions, List<Game> games) {
}
