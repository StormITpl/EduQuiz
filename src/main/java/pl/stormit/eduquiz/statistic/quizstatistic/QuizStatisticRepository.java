package pl.stormit.eduquiz.statistic.quizstatistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizStatisticRepository extends JpaRepository<QuizStatistic, UUID> {

    int findFirstByOrderByScoreDesc();
    int findFirstByOrderByScoreAsc();

}