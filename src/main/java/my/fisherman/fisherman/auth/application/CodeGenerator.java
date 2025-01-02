package my.fisherman.fisherman.auth.application;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public final class CodeGenerator {
    private static final Integer MIN_CODE_VALUE = 0;
    private static final Integer MAX_CODE_VALUE = 10;
    private static final Random random = new SecureRandom();

    public static List<Integer> generateCode(Integer codeLength) {
        return random.ints(MIN_CODE_VALUE, MAX_CODE_VALUE)
                .limit(codeLength)
                .boxed()
                .toList();
    }
}
