package my.fisherman.fisherman.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import my.fisherman.fisherman.user.api.dto.UserRequest;
import my.fisherman.fisherman.user.api.dto.UserRequest.UpdateNickname;
import my.fisherman.fisherman.user.api.dto.UserResponse;
import my.fisherman.fisherman.user.api.dto.UserResponse.HealthCheck;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "회원 가입, 탈퇴등 회원 관련 정보를 관리합니다.")
public interface UserSpecification {

    @Operation(
        summary = "회원가입",
        description = "이메일, 비밀번호, 닉네임을 입력하여 회원가입을 진행합니다.",
        requestBody = @RequestBody(
            required = true,
            description = "회원가입 정보",
            content = @Content(mediaType = "application/json")
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400 -> 변경예정", description = "인증이 완료되지 않은 이메일"),
            @ApiResponse(responseCode = "400 -> 변경예정", description = "인증 정보가 없는 이메일(인증 코드 전송 필요)")
        }
    )
    ResponseEntity<Void> signUp(UserRequest.Create request);

    @Operation(
        summary = "내 정보 조회",
        description = "회원의 정보를 조회합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보 조회 실패")
        }
    )
    ResponseEntity<UserResponse.Info> getMyInfo(Long userId);


    @Operation(
        summary = "닉네임 수정",
        description = "회원의 닉네임을 수정합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "닉네임 수정 성공"),
            @ApiResponse(responseCode = "404", description = "닉네임 수정 실패")
        }
    )
    ResponseEntity<Void> updateNickname(Long userId, UpdateNickname request);

    @Operation(
        summary = "비밀번호 수정",
        description = "비밀 번호를 수정합니다",
        responses = {
            @ApiResponse(responseCode = "200", description = "비밀번호 수정 성공"),
            @ApiResponse(responseCode = "404", description = "비밀번호 수정 실패")
        }
    )
    ResponseEntity<Void> updatePassword(Long userId, UserRequest.UpdatePassword request);

    @Operation(
        summary = "회원 id 조회 / Health Check",
        description = "회원 id를 조회합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보 조회 실패")
        }
    )
    ResponseEntity<HealthCheck> healthCheck();
}
