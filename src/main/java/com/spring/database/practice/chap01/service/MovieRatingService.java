package com.spring.database.practice.chap01.service;

import com.spring.database.practice.chap01.entity.MovieRating;
import com.spring.database.practice.chap01.repository.MovieJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieRatingService {

    // 의존 객체 설정
    private final MovieJdbcRepository movieRepository;

    // 전체 조회 요청에 대한 중간 처리
    public List<MovieRating> findAll() {
        List<MovieRating> ratingList = movieRepository.findAll();
        return ratingList;
    }

    // 개별 조회 요청에 대한 중간 처리
    public MovieRating findById(Long id) {
        MovieRating foundRating = movieRepository.findOneRating(id);

        if(foundRating == null) {
            String errormessage = "리뷰를 찾을 수 없습니다.";
            log.warn(errormessage);
            // 강제 예외 실행
            throw new RuntimeException(errormessage);
        }
        return foundRating;
    }

    // 리뷰 생성 요청에 대한 중간 처리
    public void createReview(MovieRating movie) {
        movieRepository.create(movie);
    }

    // 수정 요청에 대한 중간 처리
    // 리뷰, 평점 둘 다 수정
    public void updateReview(Long id, MovieRating movie){
        movieRepository.update(id, movie);
    }

    /*
    public void updateReview(Long id, MovieRating movie) {
        movieRepository.updateReview(id, movie);
    }

    public void updateRating(Long id, MovieRating movie) {
        movieRepository.updateRating(id, movie);
    }
    */


    // 삭제 요청에 대한 중간 처리
    public void deleteReview(Long id) {
        boolean delete = movieRepository.delete(id);
        if(!delete) {
            String errormessage = "삭제할 리뷰를 찾을 수 없습니다.";
            log.warn(errormessage);
            // 강제 예외 실행
            throw new RuntimeException(errormessage);
        }
    }

}
