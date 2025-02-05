package my.fisherman.fisherman.user.domain;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("비밀번호 변경 테스트_원래 비밀번호와 다를경우")
    void updatePasswordTest_differentOriginPassword() {
        // given
        var user = User.of("test", "originPassword", "nickname");

        // when
        String originPassword = "differentOriginPassword";
        String newPassword = "newPassword";
        // then
        assertThatThrownBy(() -> user.updatePassword(originPassword, newPassword))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("기존 비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("비밀번호 변경 테스트_원래 비밀번호와 새로운 비밀번호와 같을경우")
    void updatePasswordTest_sameOriginPasswordAndNewPassword() {
        // given
        var user = User.of("test", "originPassword", "nickname");

        // when

        String originPassword = "originPassword";
        String newPassword = "originPassword";
        // then
        assertThatThrownBy(() -> user.updatePassword(originPassword, newPassword))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("기존 비밀번호와 새 비밀번호가 동일합니다.");
    }

    @Test
    @DisplayName("비밀번호 변경 테스트_정상적인 비밀번호 변경")
    void updatePasswordTest() {
        // given
        var user = User.of("test", "originPassword", "nickname");

        // when
        String originPassword = "originPassword";
        String newPassword = "newPassword";
        // then
        assertThatNoException().isThrownBy(() -> user.updatePassword(originPassword, newPassword));
    }

}