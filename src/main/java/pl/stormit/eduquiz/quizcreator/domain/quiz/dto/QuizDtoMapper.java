package pl.stormit.eduquiz.quizcreator.domain.quiz.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.quiz.Quiz;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizDtoMapper {

    QuizDto mapQuizEntityToQuizDto(Quiz quiz);

    List<QuizDto> mapQuizListOfEntityToQuizDtoList(List<Quiz> quizzesList);

}
