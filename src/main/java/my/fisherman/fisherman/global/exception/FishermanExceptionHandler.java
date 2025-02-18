package my.fisherman.fisherman.global.exception;

import io.swagger.v3.oas.annotations.Hidden;
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
}
