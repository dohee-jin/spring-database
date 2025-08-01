package com.spring.database.querydsl.dto;

import com.querydsl.core.Tuple;
import com.spring.database.querydsl.entity.QIdol;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 그룹명과 평균나이를 매핑할 클래스
public class GroupAverageAge {
    private String groupName;
    private double averageAge;

    // 튜플을 전달받아서 dto로 변환하는 생성자

    public GroupAverageAge(Tuple tuple) {
        this.groupName = tuple.get(QIdol.idol.group.groupName);
        this.averageAge = tuple.get(QIdol.idol.age.avg());
    }

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
