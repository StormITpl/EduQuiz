package pl.stormit.eduquiz.stats.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.stormit.eduquiz.stats.service.QuizStatsService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stats")
public class QuizStatsApi {

    private final QuizStatsService service;

    @GetMapping
    public List<String> getAllQuizzesSeparated() {
        return service.countAllPlayedGameSeparatedByQuiz();
    }

    @GetMapping("/all")
    public long getAllQuizzes() {
        return service.countAllPlayedGame();
    }

    @GetMapping("/fast")
    public List<String> getAllFastestQuizzes() {
        return  service.getAllQuizzesWithShortestDuration();
    }

    @GetMapping("/{quizId}")
    public String get(@PathVariable UUID quizId) {
        return service.countAllPlayedGameForQuiz(quizId);
    }


}
