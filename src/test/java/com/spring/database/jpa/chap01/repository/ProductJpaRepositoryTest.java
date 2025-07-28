package com.spring.database.jpa.chap01.repository;

import com.spring.database.jpa.chap01.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.spring.database.jpa.chap01.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // -> 스프링이 관리하는 모든 빈을 담당
// @Transactional
//@Rollback // 테스트가 끝나면 DML 을 취소

// @DataJpaTest // repository 빈만 로딩, 롤백옵션 자동포함, 내장 DB 에서만 사용가능 (h2 database)
class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    // 테스트 전에 자동으로 실행될 코드
    @BeforeEach
    void insertBefore() {
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(2000000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("주먹밥")
                .category(FOOD)
                .price(1500)
                .build();

        // 전부 insert 하고 commit(확정) 까지
        productJpaRepository.saveAllAndFlush(List.of(p1, p2, p3, p4));
    }

    @Test
    @DisplayName("")
    void saveTest() {
        // given
        Product newProduct = Product.builder()
                .name("청바지")
                .category(FASHION)
                .price(35000)
                .build();
        // when
        Product save = productJpaRepository.save(newProduct);

        // then
        assertNotNull(save);
    }

    @Test
    @DisplayName("1번 상품을 삭제한다.")
    void deleteTest() {
        // given
        Long id = 1L;
        // when
        productJpaRepository.deleteById(id);
        // then
    }


    @Test
    @DisplayName("3번 상품을단일조회하면 그 상품명은 구두이다.")
    void findOneTest() {
        // given
        Long id = 3L;
        // when
        Optional<Product> foundProduct = productJpaRepository.findById(id);
        // orElseThrow, orElse


        // then
        System.out.println("foundProduct = " + foundProduct.map(product -> product.getName()).orElse("상품 없음"));
    }

    @Test
    @DisplayName("2번 상품의 이름과 가격을 수정한다.")
    void updateTest() {
        // given
        Long id = 2L;
        String newName = "에어프라이어";
        int newPrice = 120000;
        Product.Category newCategory = ELECTRONIC;

        // when
        /*
            JAP 에서는 수정메소드를 따로 제공하지 않습니다.
            단일 조회를 수행한 후 setter를 통해 값을 변경하고
            다시 save를 하면 insert 문 대신에 update 문이 나갑니다.
         */
         Product foundProduct = productJpaRepository.findById(id).orElseThrow();
         /*
            foundProduct.setName(newName);
            foundProduct.setPrice(newPrice);
            foundProduct.setCategory(newCategory);
         */
        foundProduct.changeProduct(newName, newPrice, newCategory);

         productJpaRepository.save(foundProduct);
        // then
    }

}