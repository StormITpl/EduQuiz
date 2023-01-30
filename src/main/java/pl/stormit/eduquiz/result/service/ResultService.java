package pl.stormit.eduquiz.result.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.game.domain.repository.GameRepository;
import pl.stormit.eduquiz.result.domain.model.Result;
import pl.stormit.eduquiz.result.domain.repository.ResultRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    private final GameRepository gameRepository;


    public Optional<Result> getResult(UUID id) {
        return resultRepository.findById(id);
    }

    private Integer getScore(UUID gameId) {

        return 0;
    }

}
