package pl.stormit.eduquiz.quizcreator.domain.quiz.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;


@Mapper(componentModel = "spring")
public interface QuizCreationMapper {

    QuizCreationDto mapQuizEntityToQuizCreationDto(Quiz quiz);
}
