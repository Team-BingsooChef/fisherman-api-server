package my.fisherman.fisherman.user.domain;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import my.fisherman.fisherman.global.exception.FishermanException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("비밀번호 변경 테스트_원래 비밀번호와 다를경우")
    void updatePasswordTest_differentOriginPassword() {
        // given
        User user = User.of("test", "originPassword", "nickname");

        // when
        String originPassword = "differentOriginPassword";
        String newPassword = "newPassword";
        // then
        assertThatThrownBy(() -> user.updatePassword(originPassword, newPassword))
            .isInstanceOf(FishermanException.class);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트_원래 비밀번호와 새로운 비밀번호와 같을경우")
    void updatePasswordTest_sameOriginPasswordAndNewPassword() {
        // given
        User user = User.of("test", "originPassword", "nickname");

        // when

        String originPassword = "originPassword";
        String newPassword = "originPassword";
        // then
        assertThatThrownBy(() -> user.updatePassword(originPassword, newPassword))
            .isInstanceOf(FishermanException.class);

    }

    @Test
    @DisplayName("비밀번호 변경 테스트_정상적인 비밀번호 변경")
    void updatePasswordTest() {
        // given
        User user = User.of("test", "originPassword", "nickname");

        // when
        String originPassword = "originPassword";
        String newPassword = "newPassword";
        // then
        assertThatNoException().isThrownBy(() -> user.updatePassword(originPassword, newPassword));
    }

}