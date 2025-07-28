package com.spring.database.jpa.chap02;

import com.spring.database.chap02.entity.Product;
import com.spring.database.jpa.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository의 제네릭에는 첫번째 엔터티, 두번째 ID 타입을 입력
public interface StudentRepository extends JpaRepository<Student, String> {

    // Query Method: 메소드에 특별한 이름 규칙을 사용해서 SQL을 생성

    // 이름으로 조회
    List<Student> findByName(String name);

    // 도시, 이름으로 조회
    List<Student> findByCityAndMajor(String city, String name);

    // LIKE ?%%
    List<Student> findByMajorStartingWith(String major);

    // LIKE %?% ..?
    List<Student> findByMajorContaining(String major);

    // LIKE ..%?
    List<Student> findByMajorEndingWith(String major);

    // WHERE age <= ?
   // List<Student> findByAgeLessThanEqual(int age);

}
