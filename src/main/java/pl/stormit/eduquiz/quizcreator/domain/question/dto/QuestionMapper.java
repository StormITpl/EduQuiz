package pl.stormit.eduquiz.quizcreator.domain.question.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.answer.Answer;
import pl.stormit.eduquiz.quizcreator.domain.answer.dto.AnswerDto;
import pl.stormit.eduquiz.quizcreator.domain.question.Question;

import java.util.List;


@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDto mapQuestionEntityToQuestionDto(Question question);

    List<QuestionDto> mapQuestionEntityToQuestionDtoList(Iterable<Question> questionList);
}
