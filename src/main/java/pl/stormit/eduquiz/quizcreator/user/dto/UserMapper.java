package pl.stormit.eduquiz.quizcreator.user.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.user.domain.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapUserEntityToUserDto(User user);
}
