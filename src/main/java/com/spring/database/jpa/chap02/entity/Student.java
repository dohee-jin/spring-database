package com.spring.database.jpa.chap02.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter // @Setter  // 세터는 객체의 불변성을 해칠수 도 있음
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // pk를 string으로

    @Column(name = "stu_name", nullable = false)
    private String name;

    private String city;

    private String major;
}
