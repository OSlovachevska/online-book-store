package slovachevska.onlinebookstore.service.user;

import slovachevska.onlinebookstore.dto.user.UserRegistrationRequest;
import slovachevska.onlinebookstore.dto.user.UserRegistrationResponseDto;
import slovachevska.onlinebookstore.exception.RegistrationException;

public interface UserService {

    UserRegistrationResponseDto register(UserRegistrationRequest
                                                 request) throws RegistrationException;
}
