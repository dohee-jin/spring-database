package com.spring.database.jpa.chap03.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString(exclude = {"department"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder


// 사원: N
@Entity(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id; // 사원번호

    @Column(name = "emp_name", nullable = false)
    private String name; // 사원명

    // 부서정보를 통째로 포함
    // jpa 연관관계 설정
    // 사원 > 부서, 단방향 매핑
    // DBMS 처럼 한쪽(N쪽)에 상대의 데이터를 포함시키는 전략
    // -> 단방향 매핑
    // ManyToOne 은 무조건 LAZY 를 걸어라
    @ManyToOne(fetch = FetchType.LAZY) // 필요없을 때는 조인을 하지 않는 전략
    @JoinColumn(name = "dept_id") // FK 를 포함시키는건 DB 패러다임에 맞춰야 함
    private Department department;

}
