package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    UserRequestDto mapUserEntityToUserRequestDto(User user);
}
