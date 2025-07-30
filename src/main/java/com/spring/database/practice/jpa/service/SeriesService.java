package com.spring.database.practice.jpa.service;

import com.spring.database.practice.jpa.entity.Series;
import com.spring.database.practice.jpa.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.spring.database.practice.jpa.entity.Series.Genre.DRAMA;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    // 새로운 시리즈 생성
    /*public createSeries() {
        Series newSeries = Series.builder()
                .title("스킵과 로퍼")
                .genre(DRAMA)
                .releaseYear(LocalDate.of())
                .director()
                .build();

        seriesRepository.save();
    }*/

    // 특정 시리즈의 모든 에피소드 조회

    // 시리즈 삭제 시 모든 에피소드도 함께 삭제

    // 장르별 시리즈 목록 조회
}
