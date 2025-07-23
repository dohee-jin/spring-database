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
class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    // 조회 테스트
    @Test
    @DisplayName("영화 리뷰 목록 전체를 데이터베이스에서 조회하여 리스트를 반환한다.")
    void findAllTest() {
        // given
        // when
        List<MovieRating> ratingList = movieRepository.findAll();
        // then
        assertEquals(1, ratingList.size());
        assertEquals("명탐정코난:척안의잔상", ratingList.get(0).getMovieTitle());
    }

    // 개별 조회 테스트
    @Test
    @DisplayName("id로 개별 리뷰를 데이터베이스에서 조회하여 해당하는 객체를 반환한다.")
    void findByIdTest() {
        // given
        Long id = 1L;
        // when
        MovieRating foundOne = movieRepository.findOneRating(id);
        // then
        assertNotNull(foundOne);
        assertEquals("명탐정코난:척안의잔상", foundOne.getMovieTitle());
        System.out.println("movie review = " + foundOne);
    }

    // 리뷰 추가 생성 테스트
    @Test
    @DisplayName("영화 리뷰를 추가하여 데이터베이스에 저장한다.")
    void createdTest() {
        // given
        MovieRating givenReview = MovieRating.builder()
                .movieTitle("괴물")
                .rating(3)
                .review("뿌우뿌우뿌우")
                .watchedAt(LocalDate.of(2022, 07, 06))
                .build();
        // when
        boolean flag = movieRepository.create(givenReview);
        // then
        Assertions.assertTrue(flag);
    }

    // 리뷰, 별점 수정 테스트
    @Test
    @DisplayName("리뷰와 별점을 모두 수정한다.")
    void updateAllTest() {
        // given
        MovieRating updateAll = MovieRating.builder()
                .rating(2)
                .review("아카이슈이치가안나오다니코난은그만완결내라")
                .id(1L)
                .build();
        // when
        boolean flag = movieRepository.updateAll(updateAll.getId(), updateAll);
        // then
        Assertions.assertTrue(flag);
    }

    // 리뷰 수정 테스트
    @Test
    @DisplayName("리뷰만 수정한다.")
    void updateReviewTest() {
        // given
        MovieRating updateReview = MovieRating.builder()
                .review("홍산오살려네--;;")
                .id(2L)
                .build();
        // when
        boolean flag = movieRepository.updateReview(updateReview.getId(), updateReview);
        // then
        Assertions.assertTrue(flag);
    }

    // 별점 수정 테스트
    @Test
    @DisplayName("별점을 수정한다.")
    void updateRatingTest() {
        // given
        MovieRating updateRating = MovieRating.builder()
                .rating(5)
                .id(2L)
                .build();
        // when
        boolean flag = movieRepository.updateRating(updateRating.getId(), updateRating);
        // then
        Assertions.assertTrue(flag);
    }

    // 삭제 테스트
    @Test
    @DisplayName("id에 해당하는 리뷰를 삭제한다.")
    void deleteTest() {
        // given
        Long id = 2L;
        // when
        boolean flag = movieRepository.delete(id);
        // then
        Assertions.assertTrue(flag);
    }
}