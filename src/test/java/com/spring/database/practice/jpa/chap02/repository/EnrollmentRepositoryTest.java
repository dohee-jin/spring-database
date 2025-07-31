package com.spring.database.practice.jpa.chap02.repository;

import com.spring.database.practice.jpa.chap02.entity.Course;
import com.spring.database.practice.jpa.chap02.entity.Enrollment;
import com.spring.database.practice.jpa.chap02.entity.Students;
import com.spring.database.practice.jpa.chap02.entity.Students;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class EnrollmentRepositoryTest {

    @Autowired
    StudentsRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    EntityManager em;


    @BeforeEach
    void setUp() {
        Students s1 = Students.builder()
                .name("김철수")
                .email("cheolsu@gmail.com")
                .build();

        Students s2 = Students.builder()
                .name("이영희")
                .email("yeonghee@gmail.com")
                .build();


        Course c1 = Course.builder()
                .title("Spring Boot 기초")
                .instructor("스프링")
                .price(150000)
                .build();

        Course c2 = Course.builder()
                .title("React 심화")
                .instructor("리액트")
                .price(200000)
                .build();

        Enrollment e1 = Enrollment.builder()
                .student(s1)
                .course(c1)
                .progressRate(0)
                .completed(false)
                .build();

        Enrollment e2 = Enrollment.builder()
                .student(s2)
                .course(c1)
                .progressRate(0)
                .completed(false)
                .build();


        Enrollment e3 = Enrollment.builder()
                .student(s1)
                .course(c2)
                .progressRate(0)
                .completed(false)
                .build();

        studentRepository.saveAllAndFlush(List.of(s1, s2));
        courseRepository.saveAllAndFlush(List.of(c1, c2));

        enrollmentRepository.save(e1);
        enrollmentRepository.save(e2);
        enrollmentRepository.save(e3);

        em.flush();
        em.close();

    }

    @Test
    @DisplayName("특정 학생이 신청한 강의만 조회, 특정 강의를 수강하는 학생이 2명인지 확인")
    void findEnrollTest() {
        // given
        em.flush();
        em.clear();

        // when
        // 김철수가 수강신청한 강의가 2개 인지 확인
        Students student = studentRepository.findById(1L).orElseThrow();
        List<Enrollment> enrollmentList = student.getEnrollmentList();

        for(Enrollment e : enrollmentList) {
            System.out.printf("\n과목명: %s\n", e.getCourse().getTitle());
        }

        // then
        System.out.println("student = " + student);
    }
}