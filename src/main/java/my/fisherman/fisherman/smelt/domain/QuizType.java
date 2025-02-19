package my.fisherman.fisherman.smelt.domain;

import my.fisherman.fisherman.global.exception.FishermanException;
import my.fisherman.fisherman.global.exception.code.SmeltErrorCode;

public enum QuizType {
    OX,
    MULTIPLE;

    public static QuizType getQuizType(String quizType) {
        for (QuizType value : QuizType.values()) {
            if (value.toString().equals(quizType)) {
                return value;
            }
        }

        throw new FishermanException(SmeltErrorCode.NOT_MATCH, "퀴즈의 타입은 'OX', 'MULTIPLE' 중 하나여야 합니다.");
    }
}
