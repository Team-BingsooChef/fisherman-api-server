package my.fisherman.fisherman.global.exception.code;

import org.springframework.http.HttpStatus;

public enum SmeltErrorCode implements ErrorCode {
    NOT_MINE(HttpStatus.BAD_REQUEST, "SM003", "본인의 낚시터에는 빙어를 보낼 수 없습니다"),
    BAQ_QUESTION(HttpStatus.BAD_REQUEST, "SM004", "빙어의 선지가 아닙니다."),
    YET_SOLVED(HttpStatus.BAD_REQUEST, "SM011", "아직 풀지 않은 빙어입니다."),
    YET_READ(HttpStatus.BAD_REQUEST, "SM012", "아직 읽지 않은 빙어입니다."),
    
    ALREADY_SOLVED(HttpStatus.BAD_REQUEST, "SM021", "이미 해결된 퀴즈입니다."),
    ALREADY_COMMENT(HttpStatus.BAD_REQUEST, "SM022", "이미 댓글이 있는 편지입니다."),

    NOT_MATCH(HttpStatus.BAD_REQUEST, "SM031", "주어진 문자열을 매치할 수 없습니다."),

    FORBIDDEN(HttpStatus.FORBIDDEN, "SM301", "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "SM401", "자원을 찾을 수 없습니다.");

    private HttpStatus httpStatus;
    private String code;
    private String message;

    SmeltErrorCode(HttpStatus httpStatus, String code, String message) {
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
