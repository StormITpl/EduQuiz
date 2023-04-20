package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerDto mapAnswerEntityToAnswerDto(Answer answer);

    List<AnswerDto> mapAnswerEntityToAnswerDtoList(Iterable<Answer> answerList);
}
