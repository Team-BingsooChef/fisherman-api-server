package my.fisherman.fisherman.global.exception.code;

import org.springframework.http.HttpStatus;

public enum FishingSpotErrorCode implements ErrorCode {
    
    FORBIDDEN(HttpStatus.FORBIDDEN, "SM301", "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "SM401", "자원을 찾을 수 없습니다.");

    private HttpStatus httpStatus;
    private String code;
    private String message;

    FishingSpotErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

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
