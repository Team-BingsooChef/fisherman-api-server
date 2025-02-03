package my.fisherman.fisherman.auth.application.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CodeGeneratorTest {

    @ParameterizedTest(
            name = "코드 길이 {0}에 대해 정상적으로 코드를 생성한다."
    )
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("코드 생성 테스트")
    void generateCode(Integer codeLength) {
        // given

        // when
        var code = CodeGenerator.generateCode(codeLength);

        // then
        assertThat(code).hasSize(codeLength);
    }

    @RepeatedTest(
            value = 100,
            name = "{currentRepetition}/{totalRepetitions}"
    )
    @DisplayName("코드의 범위가 0 이상 10 미만인지 확인한다.")
    void generateCodeRange() {
        // given

        // when
        var code = CodeGenerator.generateCode(1);
        // then
        assertThat(code.get(0)).isGreaterThanOrEqualTo(0);
        assertThat(code.get(0)).isLessThan(10);
    }
}