package my.fisherman.fisherman.user.application.dto;

import my.fisherman.fisherman.user.domain.User;

public class UserInfo {

    public record Simple(
        String email,
        String nickname,
        Boolean isPublic
    ) {

        public static Simple from(User user) {
            return new Simple(
                user.getEmail(),
                user.getNickname(),
                user.getIsPublic()
            );
        }
    }

    public record Detail(
        Long userId,
        String email,
        String nickname
    ) {

        public static Detail from(User user) {
            return new Detail(
                user.getId(),
                user.getEmail(),
                user.getNickname()
            );
        }
    }

}
