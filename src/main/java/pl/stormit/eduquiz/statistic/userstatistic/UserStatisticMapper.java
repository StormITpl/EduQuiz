package pl.stormit.eduquiz.statistic.userstatistic;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.statistic.userstatistic.dto.UserStatisticDto;

@Mapper(componentModel = "spring")
interface UserStatisticMapper {
    
    UserStatisticDto mapUserStatisticsEntityToUserStatisticsDto (UserStatistic userStatistics);
}
