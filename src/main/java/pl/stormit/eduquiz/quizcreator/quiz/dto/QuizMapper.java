package pl.stormit.eduquiz.quizcreator.quiz.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.quiz.domain.model.Quiz;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    QuizDto mapQuizEntityToQuizDto(Quiz quiz);

}
