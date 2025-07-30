package com.spring.database.practice.jpa.repository;

import com.spring.database.practice.jpa.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
