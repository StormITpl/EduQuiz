package pl.stormit.eduquiz.quizcreator.domain.question.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;


@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDto mapQuestionEntityToQuestionDto(Question question);
}
