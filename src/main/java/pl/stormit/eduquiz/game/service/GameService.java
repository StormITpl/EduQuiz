package pl.stormit.eduquiz.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.dto.GameDto;
import pl.stormit.eduquiz.quizcreator.quiz.dto.QuizDto;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    // To be changed
    public GameDto createGame(QuizDto quizRequest) {
        return new GameDto(UUID.randomUUID(), List.of(UUID.randomUUID()));
    }
}