package pl.stormit.eduquiz.game.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.game.domain.entity.Game;

@Mapper(componentModel = "spring")
public interface GameIdMapper {

    GameIdDto mapGameEntityToGameIdDto(Game game);
}
