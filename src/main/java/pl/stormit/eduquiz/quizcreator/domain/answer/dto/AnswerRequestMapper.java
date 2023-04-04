package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;

@Mapper(componentModel = "spring")
public interface AnswerRequestMapper {

    AnswerRequestDto mapAnswerEntityToAnswerRequestDto (Answer answer);
}
