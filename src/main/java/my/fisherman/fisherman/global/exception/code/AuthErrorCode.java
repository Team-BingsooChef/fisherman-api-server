package my.fisherman.fisherman.global.exception.code;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    EMAIL_NOT_VERIFIED(HttpStatus.FORBIDDEN, "A401", "이메일 인증이 필요합니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "A402", "이미 가입된 이메일입니다."),
    INVALID_AUTH_REQUEST(HttpStatus.BAD_REQUEST, "A403", "인증 정보가 존재하지 않습니다."),
    INVALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "A403", "인증 코드가 일치하지 않습니다."),
    INVALID_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "A404", "지원하지 않는 OAuth 제공자입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "A405", "유효하지 않은 토큰입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "A406", "비밀번호가 일치하지 않습니다."),
    SAME_PASSWORD(HttpStatus.BAD_REQUEST, "A407", "이전 비밀번호와 동일합니다."),
    EMAIL_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "A408", "이메일 전송에 실패했습니다.");

    private HttpStatus httpStatus;
    private String code;
    private String message;

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
