package slovachevska.onlinebookstore.mapper;

import org.mapstruct.Mapper;
import slovachevska.onlinebookstore.config.MapperConfig;
import slovachevska.onlinebookstore.dto.user.UserRegistrationRequest;
import slovachevska.onlinebookstore.dto.user.UserRegistrationResponseDto;
import slovachevska.onlinebookstore.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserRegistrationResponseDto toDto(User user);

    User toModel(UserRegistrationRequest requestDto);
}
