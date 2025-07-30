package com.spring.database.practice.jpa.repository;

import com.spring.database.practice.jpa.entity.Episode;
import com.spring.database.practice.jpa.entity.Series;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.spring.database.practice.jpa.entity.Series.Genre.DRAMA;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class EpisodeRepositoryTest {

    @Autowired EpisodeRepository episodeRepository;
    @Autowired SeriesRepository seriesRepository;
    @Autowired EntityManager em;

    @BeforeEach
    void insertDummy() {

        // 시리즈 만들기
        Series s1 = Series.builder()
                .title("스토브리그")
                .genre(DRAMA)
                .releaseYear(LocalDate.of(2019, 01, 01))
                .director("감독")
                .build();

        List<Series> seriesList = seriesRepository.saveAllAndFlush(List.of(s1));

        // 에피소드 만들기
        Episode ep1 = Episode.builder()
                .episodeNumber(1)
                .title("1화")
                .duration(30)
                .description("1화입니다.")
                .series(seriesList.get(0))
                .build();

        Episode ep2 = Episode.builder()
                .episodeNumber(2)
                .title("2화")
                .duration(30)
                .description("2화입니다.")
                .series(seriesList.get(0))
                .build();

        episodeRepository.saveAllAndFlush(List.of(ep1, ep2));

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("시리즈를 조회하면 에피소드 목록이 함께 조회된다.")
    void findTest() {
        // given
        Long id = 1L;
        // when
        Series series = seriesRepository.findById(id).orElseThrow();
        // then
        System.out.println("series = " + series);
        List<Episode> episode = series.getEpisode();
        episode.forEach(System.out::println);
    }

}