package pl.stormit.eduquiz.statistic.quizstatistic;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.statistic.quizstatistic.dto.QuizStatisticDto;

@Mapper(componentModel = "spring")
interface QuizStatisticMapper {
    QuizStatisticDto mapQuizStatisticEntityToQuizStatisticDto(QuizStatistic quizStatistic);
}
