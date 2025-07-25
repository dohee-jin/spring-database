package com.spring.database.practice.chap01.repository;

import com.spring.database.practice.chap01.entity.MovieRating;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieSpringRepository implements MovieRepository{

    // 스프링 JDBC 연결을 위해 템플릿을 의존객체로 생성
    private final JdbcTemplate template;

    // 생성
    @Override
    public boolean create(MovieRating movie) {
        String sql = """
                    INSERT INTO movie_ratings
                    (movie_title, rating, review, watched_at)
                    VALUES 
                        (?, ?, ?, ?)
                """;

        return template.update(sql,
                movie.getMovieTitle(),
                movie.getRating(),
                movie.getReview(),
                movie.getWatchedAt()
        ) == 1;
    }

    // 전체 조회
    @Override
    public List<MovieRating> findAll() {

        String sql = """
                    SELECT * FROM movie_ratings
                """;

        return template.query(sql, new RowMapper<MovieRating>() {
            @Override
            public MovieRating mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MovieRating(rs);
            }
        });

    }

    // 개별 조회
    @Override
    public MovieRating findOneRating(Long id) {

        String sql = """
                SELECT * FROM movie_ratings
                WHERE id = ?
                """;
        return template.queryForObject(sql, new RowMapper<MovieRating>() {
            @Override
            public MovieRating mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MovieRating(rs);
            }
        }, id);
    }

    // 수정
    @Override
    public boolean update(Long id, MovieRating movie) {

        String sql = """
                UPDATE movie_ratings
                SET movie_title = ?,
                    rating = ?,
                    review = ?
                WHERE id = ?
                """;
        return template.update(sql,
                movie.getMovieTitle(),
                movie.getRating(),
                movie.getReview(),
                id
                ) == 1;
    }

    // 삭제
    @Override
    public boolean delete(Long id) {
        String sql = """
                DELETE FROM movie_ratings
                WHERE id = ?
                """;
        return template.update(sql, id) == 1;
    }
}
