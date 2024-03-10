package pl.stormit.eduquiz.statistic.quizstatistic;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.stormit.eduquiz.game.domain.entity.Game;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.quizcreator.domain.user.Role;
import pl.stormit.eduquiz.quizcreator.domain.user.Status;
import pl.stormit.eduquiz.quizcreator.domain.user.User;
import pl.stormit.eduquiz.quizcreator.domain.user.UserRepository;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles({"test"})
@Transactional
@SpringBootTest
class QuizStatisticFacadeImpIntegrationTestAdditional {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private QuizStatisticRepository quizStatisticRepository;

    @Autowired
    private QuizStatisticService quizStatisticService;

//    @Autowired
//    private QuizStatisticFacadeImp quizStatisticFacadeImp;

//    @Autowired
//    private QuizService quizService;

    private  int score;
//    private  final String USER_NAME = "user";
//    private  final UUID USER_UUID = UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82501");
    private  final UUID STAT_UUID = UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82505");
    private  final UUID GAME_UUID = UUID.fromString("f825606e-c660-4675-9a3a-b19e77c82508");
    private  final Game game = new Game();
    private  final User firstUser = new User();
    private  final QuizStatistic statistic = new QuizStatistic();

    @BeforeEach
    void SetUp(){
        score = 10;

        game.setId(GAME_UUID);
        game.setCreatedAt(LocalDateTime.now().minusSeconds(10));

        firstUser.setNickname("Ananiasz");
        firstUser.setEmail("ananiasz@gmail.com");
        firstUser.setPassword("Password123!");
        firstUser.setStatus(Status.VERIFIED);
        firstUser.setRole(Role.ROLE_ADMIN);
        userRepository.save(firstUser);

        statistic.setCreatedAt(LocalDateTime.now());
        statistic.setDuration(ChronoUnit.SECONDS.between(game.getCreatedAt(), LocalDateTime.now()));
        statistic.setScore(score);
        statistic.setUserId(firstUser.getId());
        statistic.setId(STAT_UUID);

    }

    @Test
    void shouldReturnHighestScore(){
        // given

        userRepository.save(firstUser);
        gameRepository.save(game);
        List<Game> all = gameRepository.findAll();
        UUID gameRepId = all.get(0).getId();
        Optional<Game> byId = gameRepository.findById(gameRepId);
        statistic.setGame(byId.get());
        quizStatisticRepository.save(statistic);

        int highestScore = quizStatisticService.getHighestScore();

        assertEquals(10,highestScore);
    }
}