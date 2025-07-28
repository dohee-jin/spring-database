package com.spring.database.jpa.chap01.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Locale.Category;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity // 이 클래스는 데이터베이스 테이블과 1:1로 매칭되는 클래스
@Table(name = "tbl_product")
public class Product {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    @Column(name = "prod_nm", length = 30, nullable = false)
    private String name;

    @Column(name = "prod_price")
    private int price;

    @CreationTimestamp // Insert시 자동으로 시간을 저장
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdAt;

    @UpdateTimestamp // update 실행 시 자동으로 시간 수정
    private LocalDateTime updatedAt;

    // 열거형데이터는 따로 옵션을 안주면 숫자로 저장함.
    // FOOD: 1, FASHION: 2, ...
    @Enumerated(EnumType.STRING)
    private Category category; // 상품 카테고리

    // 수정용 편의 메소드
    public void changeProduct(String newName, int newPrice, Category newCategory) {
        this.name = newName;
        this.price = newPrice;
        this.category = newCategory
    }

    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }
}
