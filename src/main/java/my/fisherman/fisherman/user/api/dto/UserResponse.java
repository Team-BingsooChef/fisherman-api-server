package my.fisherman.fisherman.user.api.dto;

import my.fisherman.fisherman.user.application.dto.UserInfo;

public class UserResponse {

    public record Info(
        String email,
        String nickname
    ) {

        public static UserResponse.Info from(UserInfo.Simple user) {
            return new UserResponse.Info(user.email(), user.nickname());
        }
    }

    public record HealthCheck(
        Long userId
    ) {

        public static HealthCheck from(UserInfo.Detail user) {
            return new HealthCheck(user.userId());
        }
    }

}
