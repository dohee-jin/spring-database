package com.spring.database.practice.chap01.repository;

import com.spring.database.practice.chap01.entity.MovieRating;

import java.util.List;

public interface MovieRepository {

    // 생성
    boolean create(MovieRating movie);

    // 전체 조회
    List<MovieRating> findAll();

    // 개별 조회
    MovieRating findOneRating(Long id);

    // 수정
    boolean update(Long id, MovieRating movie);

    // 삭제
    boolean delete(Long id);
}
