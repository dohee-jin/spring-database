package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// Spring JDBC 로 도서 CRUD를 관리
@Repository
@RequiredArgsConstructor
public class BookSpringRepository implements BookRepository {

    private final JdbcTemplate template;


    @Override
    public boolean save(Book book) {
        String sql = """
                    INSERT INTO BOOKS
                        (title, author, isbn)
                    VALUES
                        (?, ?, ?)
                    """;

        return template.update(
                sql,
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn()
        ) == 1;
    }

    @Override
    public boolean updateTitleAndAuthor(Book book) {
        String sql = """
                        UPDATE BOOKS
                        SET author = ?, title = ?
                        WHERE id = ?
                    """;

        return template.update(
                sql,
                book.getAuthor(),
                book.getTitle(),
                book.getId()) == 1;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = """
                        DELETE FROM BOOKS
                        WHERE id = ?
                    """;

        return template.update(sql, id) == 1;
    }

    @Override
    public List<Book> findAll() {

        String sql = """
                        SELECT * FROM BOOKS
                    """;

        /*return  template.query(sql, new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Book(rs);
            }
        });*/

        return  template.query(sql,
            (ResultSet rs, int rowNum) -> new Book(rs)
        );
    }

    @Override
    public Book findById(Long id) {
        String sql = """
                        SELECT * FROM BOOKS
                        WHERE id = ?
                    """;
        return template.queryForObject(sql, (ResultSet rs, int rowNum)
                -> new Book(rs), id
        );
    }
}
