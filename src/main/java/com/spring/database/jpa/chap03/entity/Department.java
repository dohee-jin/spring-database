package com.spring.database.jpa.chap03.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
// 연관관계 필드는 순환참조 방지를 위해 제외해야 함.
@ToString(exclude = {"employees"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id;

    @Column(name = "dept_name", nullable = false)
    private String name;

    /*
       # 양방향 매핑

        - 양방향 매핑은 데이터베이스와 달리 객체지향 시스템에서 가능한 방법으로
        1대N 관계에서 1쪽에 N 데이터를 포함시킬 수 있는 방법이다.

        - 양방향 매핑에서 1쪽은 상대방 엔터티 갱신에 관여 할 수 없고
           (리스트에서 사원을 지운다고 실제 디비에서 사원이 삭제되지는 않는다는 말)
           단순히 읽기전용 (조회전용)으로만 사용하는 것이다.

        - mappedBy 에는 상대방 엔터티에 @ManyToOne에 대응되는 필드명을 꼭 적어야 함

        - CascadeType
          * PERSIST : 부모가 갱신되면 자식도 같이 갱신된다.
          - 부모의 리스트에 자식을 추가하거나 제거하면
            데이터베이스에도 반영된다.

          * REMOVE : 부모가 제거되면 자식도 같이 제거된다.
          - ON DELETE CASCADE

          * ALL : 위의 내용을 전부 포함

        - orphanRemoval : 고아 객체 삭제 - 부모와의 연결이 끊어진 자식객체를 데이터베이스에서 삭제
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    // 리스트 초기화가 안되서 직접 초기화 시켜야 함
    private List<Employee> employees = new ArrayList<>();

    // 양방향 매핑 리스트에 사원을 추가할 때 사용할 편의 메소드
    public void addEmployee(Employee employee) {
        employee.setDepartment(this);
        this.employees.add(employee);
    }

}
