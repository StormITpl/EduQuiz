package pl.stormit.eduquiz.quizcreator.domain.user.dto;

import org.mapstruct.Mapper;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapUserEntityToUserDto(User user);

    List<UserDto> mapUserEntityToUsersDtoList(Iterable<User> userList);
}
