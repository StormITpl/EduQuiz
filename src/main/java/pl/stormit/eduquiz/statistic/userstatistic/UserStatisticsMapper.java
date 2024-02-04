package pl.stormit.eduquiz.statistic.userstatistic;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.statistic.userstatistic.dto.UserStatisticsDto;

@Mapper(componentModel = "spring")
interface UserStatisticsMapper {
    
    UserStatisticsDto mapUserStatisticsEntityToUserStatisticsDto (UserStatistics userStatistics);
}
