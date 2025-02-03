package my.fisherman.fisherman.user.api.dto;

import jakarta.validation.constraints.Email;
import my.fisherman.fisherman.user.application.command.UserCommand;
import org.hibernate.validator.constraints.Length;

public class UserRequest {
    public record Create(
            @Email String email,
            @Length(min = 10, max = 15) String password,
            @Length(max = 8) String nickname
    ) {
        public UserCommand.SignUp toCommand() {
            return new UserCommand.SignUp(email, password, nickname);
        }
    }
}
