package my.fisherman.fisherman.user.api.dto;

import my.fisherman.fisherman.user.application.dto.UserInfo;

public class UserResponse {

    public record Info(
        String email,
        String nickname,
        Boolean isPublic
    ) {

        public static UserResponse.Info from(UserInfo.Simple user) {
            return new UserResponse.Info(user.email(), user.nickname(), user.isPublic());
        }
    }

    public record healthCheck(
        Long userId
    ) {

        public static UserResponse.healthCheck from(UserInfo.Detail user) {
            return new UserResponse.healthCheck(user.userId());
        }
    }

}
