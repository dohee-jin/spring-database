package com.spring.database.practice.chap01.repository;

import com.spring.database.practice.chap01.entity.MovieRating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieSpringRepositoryTest {

    @Autowired
    MovieRepository movieSpringRepository;

    // 생성 테스트
    @Test
    @DisplayName("스프링 JDBC로 영화 리뷰를 생성한다.")
    void createReviewTest() {
        // given
        MovieRating newReview = MovieRating.builder()
                .movieTitle("해피엔드")
                .rating(4)
                .review("히다카유키토나랑사귀자")
                .watchedAt(LocalDate.of(2025, 05, 02))
                .build();
        // when
        boolean flag = movieSpringRepository.create(newReview);
        // then
        Assertions.assertTrue(flag);
    }

    // 조회 테스트
    @Test
    @DisplayName("스프링 JDBC로 영화 리뷰를 전체 조회한다.")
    void findAllReview() {
        // given

        // when
        List<MovieRating> ratingList = movieSpringRepository.findAll();
        // then
        assertNotNull(ratingList);
        ratingList.forEach(System.out::println);
    }


}