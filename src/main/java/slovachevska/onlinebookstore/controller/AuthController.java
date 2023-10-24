package slovachevska.onlinebookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slovachevska.onlinebookstore.dto.user.UserLoginRequestDto;
import slovachevska.onlinebookstore.dto.user.UserLoginResponseDto;
import slovachevska.onlinebookstore.dto.user.UserRegistrationRequest;
import slovachevska.onlinebookstore.dto.user.UserRegistrationResponseDto;
import slovachevska.onlinebookstore.exception.RegistrationException;
import slovachevska.onlinebookstore.jwt.AuthenticationService;
import slovachevska.onlinebookstore.service.user.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    UserRegistrationResponseDto registerUser(@RequestBody @Valid UserRegistrationRequest request)
            throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}

