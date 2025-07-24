package com.spring.database.chap02.repository;

import com.spring.database.chap01.entity.Book;
import com.spring.database.chap02.dto.PriceInfo;
import com.spring.database.chap02.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate template;

    // 상품의 기본적인 CRUD

    // 생성
    public boolean createdProduct(Product product) {
        String sql = """
                    INSERT INTO products
                    (name, price, stock_quantity, description, seller, status)
                    VALUES(?, ?, ?, ?, ?, ?)
                """;
        return template.update(sql,
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getSeller(),
                product.getStatus()
        ) == 1;
    }

    // 수정
    public boolean updateProduct(Long id, Product product) {
        String sql = """
                UPDATE products
                SET name = ?, price = ?, stock_quantity = ?, description = ?, seller = ?
                WHERE id = ?
                """;
        return template.update(sql,
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getSeller(),
                id
        ) == 1;
    }

    // 삭제(논리 삭제 -> delete 변경)
    public boolean deleteProduct(Long id) {
        String sql = """
                UPDATE products
                SET status = 'DELETE'
                WHERE id = ?
                """;

        return template.update(sql, id) == 1;
    }

    // 전체 조회(status 가 delete 인건 조회하면 안됨)
    public List<Product> findAll() {

        String sql = """
                    SELECT * FROM products
                    WHERE status = 'ACTIVE'
                """;
        // BeanPropertyRowMapper: 테이블의 칼럼명과 엔터티 클래스의 필드명이
        // 똑같을 경우 (camel, snake 차이만 빼고) 자동 매핑해줌
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class));

    }

    // 단일 조회
    public Product findById(Long id) {
        String sql = """
                SELECT * FROM products
                WHERE id = ? AND status = 'ACTIVE' 
                """;

        /*return template.queryForObject(sql, (ResultSet rs, int rowNum)
                -> new Product(rs), id
        );*/

        return template.queryForObject(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Product(rs);
            }
        }, id);
    }

    // 전체 상품의 총액과 평균가격을 가져오는 기능
    /*public Map<String, Object> getPriceInfo() {

        String sql = """
                    SELECT 
                        SUM(price) AS "total_price", 
                        AVG(price) AS "avg_price"
                    FROM products
                    WHERE status = 'ACTIVE'
                """;

        return template.queryForObject(sql,
                (ResultSet rs, int rowNum) -> {
                int totalPrice = rs.getInt("total_price");
                double avgPrice = rs.getDouble("avg_price");

                return Map.of(
                        "total", totalPrice,
                        "avg", avgPrice
                );
            }
        );
    }*/
    public PriceInfo getPriceInfo()  {
        String sql = """
                    SELECT 
                        SUM(price) AS "total_price", 
                        AVG(price) AS "avg_price"
                    FROM products
                    WHERE status = 'ACTIVE'
                """;

        return template.queryForObject(sql, new BeanPropertyRowMapper<>(PriceInfo.class));
    }
}
