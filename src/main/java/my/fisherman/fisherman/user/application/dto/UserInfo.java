package my.fisherman.fisherman.user.application.dto;

import my.fisherman.fisherman.user.domain.User;

public class UserInfo {

    public record Simple(
        String email,
        String nickname
    ) {

        public static Simple from(User user) {
            return new Simple(
                user.getEmail(),
                user.getNickname()
            );
        }
    }

}
