package com.spring.database.chap02.repository;

import com.spring.database.chap02.dto.PriceInfo;
import com.spring.database.chap02.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductJpaRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    // 생성 테스트
    @Test
    @DisplayName("스프링 JDBC로 상품 목록을 생성한다.")
    void createTest() {
        // given
        Product newProduct = Product.builder()
                .name("눈썹")
                .price(12000)
                .stockQuantity(3)
                .description("눈썹없는데왜?뭐?")
                .seller("모나리자")
                .status("ACTIVE")
                .build();
        // when
        boolean flag = productRepository.createdProduct(newProduct);
        // then
        Assertions.assertTrue(flag);
    }

    // 수정 테스트
    @Test
    @DisplayName("스프링 JDBC로 상품 목록을 수정한다.")
    void updateTest() {
        // given
        Product updateProduct = Product.builder()
                .name("모자")
                .price(30000)
                .stockQuantity(2)
                .description("대머리나돼라!")
                .seller("수리")
                .build();

        Long id = 1L;
        // when
        boolean flag = productRepository.updateProduct(id, updateProduct);
        // then
        Assertions.assertTrue(flag);
    }

    // 삭제 테스트
    @Test
    @DisplayName("스프링 JDBC로 상품 목록을 삭제합니다")
    void deleteTest() {
        // given
        Long id = 1L;
        // when
        boolean flag = productRepository.deleteProduct(id);
        // then
        Assertions.assertTrue(flag);
    }

    // 조회 테스트
    @Test
    @DisplayName("스프링 JDBC로 상품 목록을 조회한다.")
    void findAllTest() {
        // given

        // when
        List<Product> productList = productRepository.findAll();
        // then
        productList.forEach(System.out::println);
    }

    // 단일 조회 테스트
    @Test
    @DisplayName("스프링 JDBC로 id에 해당하는 상품 목록을 조회한다.")
    void findByIdTest() {
        // given
        Long id = 2L;
        // when
        Product foundProduct = productRepository.findById(id);
        // then
        assertNotNull(foundProduct);
        assertEquals(2, foundProduct.getId());
        System.out.println(foundProduct);
    }

    // 총액, 평균 금액 조회 테스트
    @Test
    @DisplayName("총액과 평균")
    void priceTest() {
        // given

        /*
        // when
        Map<String, Object> priceInfo = productRepository.getPriceInfo();
        // then
        System.out.println("총액" + priceInfo.get("total"));
        System.out.println("평균" + priceInfo.get("avg"));
        */

        // when
        PriceInfo priceInfo = productRepository.getPriceInfo();
        //then
        System.out.println("priceInfo = " + priceInfo);
    }

}