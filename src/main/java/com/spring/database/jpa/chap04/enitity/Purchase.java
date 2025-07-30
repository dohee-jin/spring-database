package com.spring.database.jpa.chap04.enitity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString(exclude = {"user", "goods"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_mtm_purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    // 회원과 상품의 다대다관계 해소를 위해서
    // 중간테이블 Purchase는 회원과 1대 N, 상품과 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

}
