package pl.stormit.eduquiz.result.dto;


import pl.stormit.eduquiz.game.domain.entity.Game;

import java.util.UUID;

public record ResultDto(UUID id, Game game) {
}
