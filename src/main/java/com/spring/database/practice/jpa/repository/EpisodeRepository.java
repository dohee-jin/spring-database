package com.spring.database.practice.jpa.repository;

import com.spring.database.practice.jpa.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
