package my.fisherman.fisherman.global.exception;

import io.swagger.v3.oas.annotations.Hidden;

import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@Slf4j
@RestControllerAdvice
public class FishermanExceptionHandler {

    @ExceptionHandler(FishermanException.class)
    ProblemDetail handleFishermanException(FishermanException e) {
        log.error("FishermanException 발생: {} - {}", e.message(), e.detail());


        ProblemDetail problemDetail = ProblemDetail.forStatus(e.httpStatus());
        problemDetail.setTitle("[%s] %s".formatted(e.code(), e.message()));
        problemDetail.setDetail(e.detail());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String detailMessage = null;
        if (!e.getFieldErrors().isEmpty()) {
            detailMessage = e.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        }

        log.error("MethodArgumentNotValidException 발생: {}", detailMessage);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("[GL001] 올바르지 않은 요청입니다.");
        problemDetail.setDetail(detailMessage);

        return problemDetail;
    }

     @ExceptionHandler(RuntimeException.class)
     ProblemDetail handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("서버에서 알 수 없는 오류가 발생했습니다.");

        return problemDetail;
     }
}
