package slovachevska.onlinebookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import slovachevska.onlinebookstore.dto.user.UserLoginResponseDto;
import slovachevska.onlinebookstore.dto.user.UserRegistrationRequest;
import slovachevska.onlinebookstore.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserLoginResponseDto toDto (User user);

    User toModel (UserRegistrationRequest requestDto);
}
