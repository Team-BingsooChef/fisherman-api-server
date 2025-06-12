package my.fisherman.fisherman.user.domain;

public enum OAuthProvider {
    GOOGLE,
    NAVER,
    KAKAO,
    SELF;

    public static OAuthProvider of(String provider) {
        return OAuthProvider.valueOf(provider.toUpperCase());
    }
}
