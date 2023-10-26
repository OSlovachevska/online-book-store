package slovachevska.onlinebookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginRequestDto {

    @Email
    private String email;

    @NotBlank
    @Length(min = 6, max = 50)
    private String password;
}
