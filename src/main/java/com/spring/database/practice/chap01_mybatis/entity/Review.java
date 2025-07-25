package com.spring.database.practice.chap01_mybatis.entity;

/*
create table movie_ratings
        (
                id          bigint auto_increment
                        primary key,
                movie_title varchar(200) not null,
rating      int          null
check (`rating` between 1 and 10),
review      mediumtext   null,
watched_at  date         null,
constraint chk_rating_range
check (`rating` >= 1 and `rating` <= 5)
)
charset = utf8mb4;
 */

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Review {
    private Long id;
    private String movieTitle;
    private int rating;
    private String review;
    private LocalDate watchedAt;
}
