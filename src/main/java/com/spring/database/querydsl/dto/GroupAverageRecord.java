package com.spring.database.querydsl.dto;

import com.querydsl.core.Tuple;
import com.spring.database.querydsl.entity.QIdol;
import lombok.*;

@Builder
// record: 자동 보일러플레이트 생성 (getter, setter ...)
public record GroupAverageRecord(
        // 필드 입력 - 자동으로 private 으로 지정해줌
        String groupName, double averageAge
) {
    // 정적 팩토리 메소드 패턴
    // 튜플을 전달받아서 dto로 변환하는 메소드
    public static GroupAverageAge from(Tuple tuple) {
        // 생성자 사용
        // return new GroupAverageAge(tuple);

        // 빌더 패턴 사용
        return GroupAverageAge.builder()
                .groupName(tuple.get(QIdol.idol.group.groupName))
                .averageAge(tuple.get(QIdol.idol.age.avg()))
                .build();
    }
}
