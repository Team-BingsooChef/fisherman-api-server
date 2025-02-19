package my.fisherman.fisherman.global.exception;

import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class FishermanExceptionHandler {

    @ExceptionHandler(FishermanException.class)
    ProblemDetail handleFishermanException(FishermanException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(e.httpStatus());
        problemDetail.setTitle("[%s] %s".formatted(e.code(), e.message()));
        problemDetail.setDetail(e.detail());

        return problemDetail;
    }

    // @ExceptionHandler(RuntimeException.class)
    // ProblemDetail handleRuntimeException(RuntimeException e) {
    //     ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    //     problemDetail.setTitle("서버에서 알 수 없는 오류가 발생했습니다.");

    //     return problemDetail;
    // }
}
