package pl.stormit.eduquiz.statistic.userstatistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface UserStatisticsRepository extends JpaRepository<UserStatistics, UUID> {
}
