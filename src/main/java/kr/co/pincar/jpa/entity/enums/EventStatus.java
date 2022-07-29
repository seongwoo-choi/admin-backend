package kr.co.pincar.jpa.entity.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EventStatus {
    PROCEEDING("진행중"), // 사용자들에게 노출되는 상태
    STAND_BY("대기중"), // 관리자페이지에서만 저장된 상태
    END("종료"), // 종료된 이벤트(추첨 전)
    DONE("추첨완료"); // 추첨완료된 이벤트

    private String viewName;
}
