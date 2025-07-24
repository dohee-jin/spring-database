package com.spring.database.practice.chap01.repository;

import com.spring.database.practice.chap01.entity.MovieRating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor

// 역할: 데이터베이스에 접근해서 CRUD를 수행하는 객체
public class MovieJdbcRepository implements MovieRepository{

    private final DataSource dataSource;

    // Create - 영화 평점 추가
    @Override
    public boolean create(MovieRating movie) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                        INSERT INTO movie_ratings
                        (movie_title, rating, review, watched_at)
                        VALUES
                            (?, ?, ?, ?)
                    """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie.getMovieTitle());
            pstmt.setInt(2, movie.getRating());
            pstmt.setString(3, movie.getReview());
            pstmt.setDate(4, java.sql.Date.valueOf(movie.getWatchedAt()));


            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read - 리뷰 전체 조회
    @Override
    public List<MovieRating> findAll() {

        List<MovieRating> ratingsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                        SELECT * FROM movie_ratings
                    """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MovieRating movie = new MovieRating(rs);
                ratingsList.add(movie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ratingsList;
    }

    // Read - 개별 리뷰 조회
    @Override
    public MovieRating findOneRating(Long id) {
        try (Connection conn = dataSource.getConnection()){

            String sql = """
                        SELECT * FROM movie_ratings
                        WHERE id = ?
                    """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) return new MovieRating(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update - 평점/감상문 모두 수정
    @Override
    public boolean update(Long id, MovieRating movie) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                        UPDATE movie_ratings
                        SET rating = ?, review = ?
                        WHERE id = ?
                    """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movie.getRating());
            pstmt.setString(2, movie.getReview());
            pstmt.setLong(3, id);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    // Update - 평점만 수정
    public boolean updateRating(Long id, MovieRating movie) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                    UPDATE movie_ratings
                    SET rating = ?
                    WHERE id = ?
                """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, movie.getRating());
            pstmt.setLong(2, id);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update - 리뷰만 수정
    public boolean updateReview(Long id, MovieRating movie) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                    UPDATE movie_ratings
                    SET review = ?
                    WHERE id = ?
                """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movie.getReview());
            pstmt.setLong(2, id);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    */

    // Delete - 목록 삭제
    @Override
    public boolean delete(Long id) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                        DELETE FROM movie_ratings
                        WHERE id = ?
                    """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
