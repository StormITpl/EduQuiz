package pl.stormit.eduquiz.statistic.quizstatistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizStatisticRepository extends JpaRepository<QuizStatistic, UUID> {


    @Query(value = "SELECT MAX(score) FROM quiz_statistics",
    nativeQuery = true)
    int findTopQuizStatisticByScore();
    @Query(value = "SELECT MIN(score) FROM quiz_statistics",
            nativeQuery = true)
    int findLastQuizStatisticByScore();

}