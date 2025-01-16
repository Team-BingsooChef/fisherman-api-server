package my.fisherman.fisherman.user.application.command;

import my.fisherman.fisherman.user.domain.User;

public class UserCommand {
    public record SignUp(
            String email,
            String password,
            String nickname
    ) {
        public User toEntity() {
            return User.of(email, password, nickname);
        }
    }
}
