package com.spring.database.practice.jpa.chap02.repository;

import com.spring.database.practice.jpa.chap02.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
