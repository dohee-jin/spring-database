package com.spring.database.practice.chap01.api;

import com.spring.database.practice.chap01.entity.MovieRating;
import com.spring.database.practice.chap01.repository.MovieRepository;
import com.spring.database.practice.chap01.service.MovieRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/practice/movie-rating")
@RequiredArgsConstructor
public class MovieRatingController {

    // 의존 객체
    private final MovieRatingService movieRatingService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<MovieRating> ratingList = movieRatingService.findAll();
        return ResponseEntity.ok(ratingList);
    }

    // 개별 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        MovieRating movie = movieRatingService.findById(id);
        return ResponseEntity.ok(movie);
    }

    // 생성
    @PostMapping
    public ResponseEntity<?> createRating(@RequestBody MovieRating movie) {
        movieRatingService.createReview(movie);
        return ResponseEntity.ok("리뷰 등록 성공!");
    }

    // 수정 - 리뷰, 별점 둘 다 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAll(@PathVariable Long id, @RequestBody MovieRating movie) {
        movieRatingService.updateAll(id, movie);
        return ResponseEntity.ok("리뷰, 별점 수정 성공!");
    }

    // 수정 - 리뷰 수정
    @PutMapping("/{id}/reviews")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody MovieRating movie) {
        movieRatingService.updateReview(id, movie);
        return ResponseEntity.ok("리뷰 수정 성공!");
    }

    // 수정 - 별점 수정
    @PutMapping("/{id}/ratings")
    public ResponseEntity<?> updateRating(@PathVariable Long id, @RequestBody MovieRating movie) {
        movieRatingService.updateRating(id, movie);
        return ResponseEntity.ok("별점 수정 성공!");
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        movieRatingService.deleteReview(id);
        return ResponseEntity.ok("등록된 리뷰가 삭제되었습니다.");
    }

}
