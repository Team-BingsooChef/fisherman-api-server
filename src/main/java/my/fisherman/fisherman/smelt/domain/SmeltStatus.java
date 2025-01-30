package my.fisherman.fisherman.smelt.domain;

public enum SmeltStatus {
    DREW,       // 뽑음 (다른 낚시터에 보내지 않음)
    UNREAD,     // 읽지 않음. 퀴즈가 있다면 풀지 않음
    SOLVED,     // 문제를 풀었으나 읽지 않음
    READ,       // 읽음
}
