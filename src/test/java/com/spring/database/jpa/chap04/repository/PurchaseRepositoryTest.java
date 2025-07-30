package com.spring.database.jpa.chap04.repository;

import com.spring.database.jpa.chap04.enitity.Goods;
import com.spring.database.jpa.chap04.enitity.Purchase;
import com.spring.database.jpa.chap04.enitity.Users;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PurchaseRepositoryTest {

    @Autowired UsersRepository usersRepository;
    @Autowired GoodsRepository goodsRepository;
    @Autowired PurchaseRepository purchaseRepository;
    @Autowired EntityManager em;

    private Users user1;
    private Users user2;
    private Users user3;
    private Goods goods1;
    private Goods goods2;
    private Goods goods3;

    @BeforeEach
    void setUp() {
        user1 = Users.builder().name("망곰이").build();
        user2 = Users.builder().name("하츄핑").build();
        user3 = Users.builder().name("쿠로미").build();

        goods1 = Goods.builder().name("뚜비모자").build();
        goods2 = Goods.builder().name("닭갈비").build();
        goods3 = Goods.builder().name("중식도").build();

        usersRepository.saveAllAndFlush(
                List.of(user1, user2, user3)
        );

        goodsRepository.saveAllAndFlush(
                List.of(goods1, goods2, goods3)
        );
    }

    @Test
    @DisplayName("회원과 상품을 연결한 구매기록 생성 테스트")
    void createPurchaseTest() {
        // given
        Purchase purchase = Purchase.builder()
                .user(user2)
                .goods(goods1)
                .build();
        // when
        // SELECT가 안나가는 이유는? 영속성 컨텍스트 떄문
        // 메모리에 데이터가 있으면 다시 db 조회를 하지 않음
        Purchase saved = purchaseRepository.save(purchase);
        em.flush();
        em.clear();

        // then
        Users user = usersRepository.findById(saved.getUser().getId()).orElseThrow();
        //Users user = users;
        Goods goods = goodsRepository.findById(saved.getGoods().getId()).orElseThrow();
        //Goods goods = saved.getGoods();

        System.out.println("user = " + user);
        System.out.println("goods = " + goods);
    }

    @Test
    @DisplayName("특정 유저의 모든 구매 정보 목록을 조회한다.")
    void findPurchasesTest() {
        // given
        Purchase p1 = Purchase.builder()
                .user(user1)
                .goods(goods1)
                .build();

        Purchase p2 = Purchase.builder()
                .user(user1)
                .goods(goods3)
                .build();

        purchaseRepository.save(p1);
        purchaseRepository.save(p2);

        em.flush();
        em.clear();

        // when
        // 1번 유저는 무슨 상품들을 구매했을까?
        Users user = usersRepository.findById(1L).orElseThrow();
        List<Purchase> purchases = user.getPurchases();

        // then
        for(Purchase p : purchases) {
            System.out.printf("%s가 구매한 상품명: %s\n", user.getName(), p.getGoods().getName());
        }
    }

    @Test
    @DisplayName("특정 상품을 구매한 유저의 목록을 조회한다.")
    void findGoodsListTest() {
        // given
        Purchase p1 = Purchase.builder()
                .user(user2)
                .goods(goods2)
                .build();

        Purchase p2 = Purchase.builder()
                .user(user3)
                .goods(goods2)
                .build();

        purchaseRepository.save(p1);
        purchaseRepository.save(p2);

        em.flush();
        em.clear();

        // when
        // 2번 상품을 누가 샀는가?
        Goods goods = goodsRepository.findById(2L).orElseThrow();
        List<Purchase> purchases = goods.getPurchases();

        // then
        List<String> names = purchases.stream()
                .map(p -> p.getUser().getName())
                .collect(Collectors.toList());

        System.out.println("names = " + names);
    }

    @Test
    @DisplayName("회원이 탈퇴하면 구매기록이 모두 사라져야 한다.")
    void cascadeTest() {
        // given
        Purchase p1 = Purchase.builder()
                .user(user1)
                .goods(goods2)
                .build();

        Purchase p2 = Purchase.builder()
                .user(user1)
                .goods(goods3)
                .build();

        Purchase p3 = Purchase.builder()
                .user(user2)
                .goods(goods3)
                .build();

        purchaseRepository.save(p1);
        purchaseRepository.save(p2);
        purchaseRepository.save(p3);

        em.flush();
        em.clear();

        // when
        Users user1 = usersRepository.findById(1L).orElseThrow();
        List<Purchase> purchases = user1.getPurchases();

        // user1의 구매구록 리스트 확인
        System.out.println("\n\nuser1 의 구매기록: " + purchases + "\n");
        // 모든 회원의 구매기록
        System.out.println("\n\n모든 회원의 구매기록: " + purchaseRepository.findAll());

        // then
        // user1의 탈퇴 진행
        usersRepository.delete(user1);

        em.flush();
        em.clear();

        // 탈퇴 후 구매기록 재 조회
        List<Purchase> purchaseList = purchaseRepository.findAll();
        System.out.println("purchaseList = " + purchaseList);

    }
}