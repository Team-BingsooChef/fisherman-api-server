package my.fisherman.fisherman.global.exception.code;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "U401", "사용자를 찾을 수 없습니다."),
    ALREADY_EXISTS(HttpStatus.CONFLICT, "U402", "이미 존재하는 사용자입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "U403", "권한이 없습니다.");

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
