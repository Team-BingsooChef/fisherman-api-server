package my.fisherman.fisherman.global.exception;

import org.springframework.http.HttpStatus;

import my.fisherman.fisherman.global.exception.code.ErrorCode;

public class FishermanException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detail;

    public FishermanException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public FishermanException(ErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public HttpStatus httpStatus() {
        return errorCode.httpStatus();
    }

    public String code() {
        return errorCode.code();
    }

    public String message() {
        return errorCode.message();
    }

    public String detail() {
        return detail;
    }
}
