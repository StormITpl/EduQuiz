package pl.stormit.eduquiz.game.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.game.domain.entity.Game;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GameIdMapper {

        GameIdDto mapGameEntityToGameIdDto(Game game);
    }
