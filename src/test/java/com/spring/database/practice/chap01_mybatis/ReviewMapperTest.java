package com.spring.database.practice.chap01_mybatis;

import com.spring.database.practice.chap01_mybatis.entity.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewMapperTest {

    @Autowired
    ReviewMapper reviewMapper;

    // 생성 테스트
    @Test
    @DisplayName("mybatis를 이용한 영화 리뷰 목록 생성")
    void createTest() {
        // given
        Review createReview = Review.builder()
                .movieTitle("서브스턴스")
                .rating(4)
                .review("귀요미엘리자수♥️")
                .watchedAt(LocalDate.parse("2024-12-18"))
                .build();
        // when
        boolean create = reviewMapper.create(createReview);
        // then
        Assertions.assertTrue(create);
    }

    // 조회 테스트
    // 전체 조회
    @Test
    @DisplayName("mybatis를 이용한 영화 리뷰 목록 전체 조회")
    void findAllTest() {
        // given

        // when
        List<Review> reviewList = reviewMapper.findAll();
        // then
        assertNotNull(reviewList);
        assertEquals(6, reviewList.size());
        reviewList.forEach(review -> {
            System.out.println(review.getMovieTitle() + ": " + review.getReview() + ", " + review.getRating());
        });
    }

    // 개별 조회
    @Test
    @DisplayName("mybatis를 이용한 영화 리뷰 목록 단일 조회")
    void findOneReview() {
        // given
        Long id = 3L;
        // when
        Review foundReview = reviewMapper.findById(id);
        // then
        assertNotNull(foundReview);
        assertEquals("헤어질결심", foundReview.getMovieTitle());
        System.out.println(foundReview.getMovieTitle() + ": " + foundReview.getReview() + ", " + foundReview.getRating());
    }

    // 수정 테스트
    @Test
    @DisplayName("mybatis를 이용한 영화 리뷰 목록 수정")
    void updateTest() {
        // given
        Review updateReview = Review.builder()
                .movieTitle("서브스턴스")
                .rating(3)
                .review("엘리자수혐오금지")
                .id(10L)
                .build();
        // when
        boolean update = reviewMapper.update(updateReview);
        // then
        Assertions.assertTrue(update);
    }

    // 삭제 테스트
    @Test
    @DisplayName("mybatis를 이용한 영화 리뷰 목록 삭제")
    void deleteTest() {
        // given
        Long id = 4L;
        // when
        Review foundReview = reviewMapper.findById(id);
        boolean delete = reviewMapper.delete(id);
        // then
        Assertions.assertTrue(delete);
        System.out.println(foundReview.getMovieTitle()+ " 리뷰가 삭제되었습니다.");
    }
}