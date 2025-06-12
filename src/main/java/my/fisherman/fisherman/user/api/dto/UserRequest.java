package my.fisherman.fisherman.user.api.dto;

import jakarta.validation.constraints.Email;
import my.fisherman.fisherman.user.application.command.UserCommand;
import org.hibernate.validator.constraints.Length;

public class UserRequest {

    public record Create(
        @Email(message = "이메일 형식이 올바르지 않습니다.") String email,
        @Length(min = 10, max = 15, message = "비밀번호의 길이는 10 ~ 15자 사이입니다.") String password,
        @Length(max = 8, message = "닉네임의 길이는 8자 이하여야 합니다.") String nickname
    ) {

        public UserCommand.SignUp toCommand() {
            return new UserCommand.SignUp(email, password, nickname);
        }
    }

    public record UpdateNickname(
        @Length(max = 8, message = "닉네임의 길이는 8자 이하여야 합니다.") String nickname
    ) {

        public UserCommand.UpdateNickname toCommand() {
            return new UserCommand.UpdateNickname(nickname);
        }
    }

    public record UpdatePassword(
        @Length(min = 10, max = 15, message = "비밀번호의 길이는 10 ~ 15자 사이입니다.")
        String originPassword,
        @Length(min = 10, max = 15, message = "비밀번호의 길이는 10 ~ 15자 사이입니다.")
        String newPassword
    ) {

        public UserCommand.UpdatePassword toCommand() {
            return new UserCommand.UpdatePassword(originPassword, newPassword);
        }
    }
}
