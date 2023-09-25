package pl.stormit.eduquiz.quizcreator.domain.answer.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
//    AnswerDto mapAnswerEntityToAnswerDto(Answer answer);

    default AnswerDto mapAnswerEntityToAnswerDto(Answer answer) {
        return AnswerDto
                .builder()
                .id(answer.getId())
                .content(answer.getContent())
                .isCorrect(answer.isCorrect())
                .question(answer.getQuestion())
                .build();
    }
}
