package com.spring.database.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(exclude = {"idols"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    private String groupName;

    // Many가 되는 쪽에서 One을 뭐라고 부르는지에 따라 mappedBy 이름을 설정
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Idol> idols = new ArrayList<>();

    // 양방향 리스트 편의 메소드
    // 데이터 추가
    public void addIdol(Idol idol) {
        this.idols.add(idol);
        idol.setGroup(this);
    }

    // 데이터 제거
    public void removeIdol(Idol idol) {
        this.idols.remove(idol);
        idol.setGroup(null);
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }
}
