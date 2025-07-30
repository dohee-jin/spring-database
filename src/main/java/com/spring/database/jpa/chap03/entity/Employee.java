package com.spring.database.jpa.chap03.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
// 연관관계 필드는 순환참조 방지를 위해 제외해야 함.
@ToString(exclude = {"department"})
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

    // 부서 변경 편의 메소드
    public void changeDepartment(Department department) {
        // ManyToOne  필드가 변경이 일어나면 반대편쪽의 OneToMany도 같이 갱신
        this.department = department; // 사원쪽에서 부서정보를 변경
        // 양방향에서는 반대편에서도 수동으로 변경처리가 진행되어야 함.
        department.getEmployees().add(this);
    }
}
