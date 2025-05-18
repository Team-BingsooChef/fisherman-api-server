package my.fisherman.fisherman.security.application;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.fisherman.fisherman.global.exception.AuthErrorCode;
import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.security.application.command.SecurityUserCommand;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final SecurityUserService securityUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        Map<String, String> attributes = getAttributes(oAuth2User,
            userRequest.getClientRegistration().getRegistrationId());

        String username = attributes.get("email");
        String oauthProvider = userRequest.getClientRegistration().getRegistrationId();
        SecurityUserCommand.OAuthSignUp userCommand = new SecurityUserCommand.OAuthSignUp(username,
            "", oauthProvider);

        return securityUserService.getUserInfo(username, oauthProvider)
            .orElseGet(() -> securityUserService.signUp(userCommand));
    }

    private Map<String, String> getAttributes(OAuth2User oAuth2User, String oauthProvider) {
        if (oauthProvider.equals("google")) {
            return getGoogleAttributes(oAuth2User);
        } else if (oauthProvider.equals("kakao")) {
            return getKakaoAttributes(oAuth2User);
        } else if (oauthProvider.equals("naver")) {
            return getNaverAttributes(oAuth2User);
        }

        throw new FishermanException(AuthErrorCode.INVALID_OAUTH_PROVIDER);
    }

    private Map<String, String> getGoogleAttributes(OAuth2User oAuth2User) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("email", (String) oAuth2User.getAttribute("email"));
        attributes.put("name", (String) oAuth2User.getAttribute("name"));
        attributes.put("picture", (String) oAuth2User.getAttributes().getOrDefault("picture", ""));
        return attributes;
    }

    private Map<String, String> getKakaoAttributes(OAuth2User oAuth2User) {
        Map<String, String> attributes = new HashMap<>();
        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        attributes.put("email", (String) kakaoAccount.get("email"));
        attributes.put("name", (String) profile.get("nickname"));
        attributes.put("picture", (String) profile.getOrDefault("profile_image_url", ""));
        return attributes;
    }

    private Map<String, String> getNaverAttributes(OAuth2User oAuth2User) {
        Map<String, String> attributes = new HashMap<>();
        Map<String, Object> response = oAuth2User.getAttribute("response");

        attributes.put("email", (String) response.get("email"));
        attributes.put("name", (String) response.get("name"));
        attributes.put("picture", (String) response.getOrDefault("profile_image", ""));
        return attributes;
    }
}
