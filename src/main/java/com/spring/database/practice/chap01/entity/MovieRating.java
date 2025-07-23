package com.spring.database.practice.chap01.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRating {
    private Long id;

    @JsonProperty("movie_title")
    private String movieTitle;
    private Integer rating;
    private String review;

    @JsonProperty("watched_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate watchedAt;

    public MovieRating(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.movieTitle = rs.getString("movie_title");
        this.rating = rs.getInt("rating");
        this.review = rs.getString("review");
        this.watchedAt = rs.getDate("watched_at").toLocalDate();
    }
}
