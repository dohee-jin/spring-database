package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookSpringRepositoryTest {

    @Autowired
    BookSpringRepository bookSpringRepository;

    @Test
    @DisplayName("스프링 JDBC로 도서를 생성한다.")
    void saveTest() {
        // given
        Book newBook = Book.builder()
                .title("스프링 JDBC")
                .author("자바왕")
                .isbn("S001")
                .build();
        // when
        boolean flag = bookSpringRepository.save(newBook);
        // then
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("스프링 JDBC로 책 제목과 저자를 수정한다.")
    void modifyTest() {
        // given
        Book updateBook = Book.builder()
                .title("수정된 스프링 책")
                .author("수정된 자바왕")
                .id(11L)
                .build();
        // when
        boolean flag = bookSpringRepository.updateTitleAndAuthor(updateBook);
        // then
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("스프링 JDBC로 id 값을 통해 도서를 삭제한다.")
    void deleteTest() {
        // given
        Long id = 11L;
        // when
        boolean flag = bookSpringRepository.deleteById(id);
        // then
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("")
    void findAll() {
        // given

        // when
        List<Book> all = bookSpringRepository.findAll();
        // then
    }
}