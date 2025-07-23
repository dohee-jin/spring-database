package com.spring.database.chap01.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private boolean available;
    // db 스네이크 케이스를 자바는 캐멀케이스로 작성 필요
    private LocalDateTime createAt;

    public Book(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.title = rs.getString("title");
        this.author = rs.getString("author");
        this.isbn = rs.getString("isbn");
        this.available = rs.getBoolean("available");
        this.createAt = rs.getTimestamp("created_at").toLocalDateTime();
    }

}
