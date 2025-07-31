package com.spring.database.practice.jpa.chap02.repository;

import com.spring.database.practice.jpa.chap02.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository extends JpaRepository<Students, Long> {
}
