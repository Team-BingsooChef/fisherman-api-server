package my.fisherman.fisherman.auth.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import my.fisherman.fisherman.auth.api.dto.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthSpecification {

    @Operation(
        method = "GET",
        summary = "이메일 인증 코드 전송",
        description = "이메일로 인증 코드를 전송합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "이메일 주소[아이디@도메인] 형식",
            content = @Content(mediaType = "application/json")
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "인증 코드 전송 성공"),
            @ApiResponse(responseCode = "400", description = "인증 코드 전송 실패")
        }
    )
    void sendAuthCode(@RequestBody AuthRequest.Mail request);

    @Operation(
        method = "POST",
        summary = "이메일 인증 코드 확인",
        description = "이메일로 전송된 인증 코드를 확인합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "인증 코드",
            required = true,
            content = @Content(mediaType = "application/json")
        ),
        parameters = {
            @Parameter(
                name = "auth_code",
                description = "인증 코드",
                required = true,
                in = ParameterIn.QUERY
            )
        }
        ,
        responses = {
            @ApiResponse(responseCode = "200", description = "인증 코드 확인 성공"),
            @ApiResponse(responseCode = "400", description = "인증 코드 확인 실패")
        }
    )
    void verifyAuthCode(
        @RequestBody @Valid AuthRequest.Mail requestBody,
        @RequestParam("auth_code") String authCode
    );

    @Operation(
        method = "POST",
        summary = "토큰 갱신",
        description = "토큰을 갱신합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "토큰 갱신 실패")
        }
    )
    ResponseEntity<Void> refreshToken(
        @CookieValue("refresh_token") String refreshToken
    );
}
