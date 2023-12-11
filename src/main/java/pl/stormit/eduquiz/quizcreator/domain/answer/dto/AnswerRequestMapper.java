package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;

@Mapper(componentModel = "spring")
public interface AnswerRequestMapper {

default AnswerRequestDto mapAnswerEntityToAnswerRequestDto(Answer answer){

    return AnswerRequestDto
            .builder()
            .content(answer.getContent())
            //.isCorrect(answer.isCorrect())
            .question(answer.getQuestion())
            .build();
}
}
