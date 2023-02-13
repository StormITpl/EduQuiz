package pl.stormit.eduquiz.quizcreator.question.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.question.domain.model.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDto mapQuestionEntityToQuestionDto(Question question);
}
