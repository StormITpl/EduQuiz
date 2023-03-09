package pl.stormit.eduquiz.quizcreator.domain.quiz.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;


@Mapper(componentModel = "spring")
public interface QuizDtoMapper {

    QuizDto mapQuizEntityToQuizDto(Quiz quiz);
}
