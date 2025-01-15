package my.fisherman.fisherman.auth.domain;


import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        System.out.println("authCode: " + this.authCode);
        this.isVerified = false;
    }

    public boolean verify(String authCode) {
        if (this.authCode.equals(authCode)) {
            this.isVerified = true;
            return true;
        }
        return false;
    }

    public Boolean notVerified() {
        return !isVerified;
    }
}