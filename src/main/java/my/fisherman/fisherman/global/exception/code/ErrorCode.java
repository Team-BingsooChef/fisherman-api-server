package my.fisherman.fisherman.global.exception.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus httpStatus();
    String code();
    String message();
}
