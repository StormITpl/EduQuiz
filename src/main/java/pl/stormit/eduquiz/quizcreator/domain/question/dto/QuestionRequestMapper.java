package pl.stormit.eduquiz.quizcreator.domain.question.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

@Mapper(componentModel = "spring")
public interface QuestionRequestMapper {
    QuestionRequestDto mapQuestionEntityToQuestionRequestDto(Question question);
}
