package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    UserRequestDto mapUserEntityToUserRequestDto(User user);
}
