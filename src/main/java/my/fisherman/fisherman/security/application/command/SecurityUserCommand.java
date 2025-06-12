package my.fisherman.fisherman.security.application.command;

import my.fisherman.fisherman.user.domain.OAuthProvider;
import my.fisherman.fisherman.user.domain.User;

public class SecurityUserCommand {
    public record OAuthSignUp(
            String email,
            String nickname,
            String provider
    ) {
        public User toEntity() {
            return User.of(email, nickname, OAuthProvider.of(provider));
        }
    }
}
