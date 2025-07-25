package com.spring.database.practice.chap01_mybatis;

import com.spring.database.practice.chap01_mybatis.entity.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    // 리뷰 생성
    boolean create(Review review);

    // 리뷰 업데이트
    boolean update(Review review);

    // 리뷰 삭제
    boolean delete(Long id);

    // 리뷰 전체 조회
    List<Review> findAll();

    // 리뷰 단일 조회
    Review findById(Long id);
}
