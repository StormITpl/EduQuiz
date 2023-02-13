package pl.stormit.eduquiz.quizcreator.answer.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.answer.domain.model.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerDto mapAnswerEntityToAnswerDto(Answer answer);
}
