package my.fisherman.fisherman.auth.domain;


import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.fisherman.fisherman.global.exception.AuthErrorCode;
import my.fisherman.fisherman.global.exception.FishermanException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authentication {

    private String email;
    private String authCode;
    private Boolean isVerified;

    public Authentication(String email, List<Integer> authCode) {
        this.email = email;
        this.authCode = authCode.stream()
            .map(String::valueOf)
            .collect(Collectors.joining());
        this.isVerified = false;
    }

    public void verify(String authCode) {
        if (this.authCode.equals(authCode)) {
            this.isVerified = true;
            return;
        }

        throw new FishermanException(AuthErrorCode.INVALID_AUTH_CODE);
    }

    public Boolean notVerified() {
        return !isVerified;
    }
}