package my.fisherman.fisherman.global.exception.code;

import org.springframework.http.HttpStatus;

public enum InventoryErrorCode implements ErrorCode {
    LACK_OF_COIN(HttpStatus.BAD_REQUEST, "IV001", "코인이 부족합니다."),

    FORBIDDEN(HttpStatus.FORBIDDEN, "IV301", "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "IV401", "자원을 찾을 수 없습니다.");

    private HttpStatus httpStatus;
    private String code;
    private String message;

    InventoryErrorCode(HttpStatus httpStatus, String code, String message) {
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
