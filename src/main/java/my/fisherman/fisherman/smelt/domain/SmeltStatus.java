package my.fisherman.fisherman.smelt.domain;

public enum SmeltStatus {
    DREW,       // 뽑음 (다른 낚시터에 보내지 않음)
    UNREAD,     // 읽지 않음. 퀴즈가 있다면 풀지 않음
    SOLVED,     // 문제를 풀었으나 읽지 않음
    READ,       // 읽음
    
    // 퀴즈가 있는 빙어의 플로우: DREW -> UNDREAD -> READ
    // 퀴즈가 없는 빙어의 플로우: DREW -> UNREAD -> SOLVED -> READ
}
