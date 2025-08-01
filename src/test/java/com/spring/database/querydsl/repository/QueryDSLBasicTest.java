package com.spring.database.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.entity.Group;
import com.spring.database.querydsl.entity.Idol;
import com.spring.database.querydsl.entity.QGroup;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import static com.spring.database.querydsl.entity.QGroup.group;
import static com.spring.database.querydsl.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class QueryDSLBasicTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    // 순수 JPA 의 핵심객체
    @Autowired
    EntityManager em;

    // JDBC 사용을 위한 핵심객체
    @Autowired
    JdbcTemplate jdbcTemplate;

    // QueryDal 핵심객체
    @Autowired
    JPAQueryFactory factory;

    @BeforeEach
    void setUp() {

        //given
        Group riize = new Group("라이즈");
        Group nctwish = new Group("엔시티위시");

        groupRepository.save(riize);
        groupRepository.save(nctwish);

        Idol idol1 = new Idol("쇼타로", 26, riize);
        Idol idol2 = new Idol("성찬", 25, riize);
        Idol idol3 = new Idol("시온", 24, nctwish);
        Idol idol4 = new Idol("리쿠", 23, nctwish);

        riize.addIdol(idol1);
        riize.addIdol(idol2);

        nctwish.addIdol(idol3);
        nctwish.addIdol(idol4);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);

        em.flush();
        em.close();

    }

    @Test
    @DisplayName("순수 JPQL로 특정 이름의 아이돌 조회하기")
    void rawJpqlTest() {
        // given
        String jpql = """
                SELECT i
                FROM Idol i
                WHERE i.idolName = ?1
                """;
        // when
        Idol foundIdol = em.createQuery(jpql, Idol.class)
                .setParameter(1, "시온")
                .getSingleResult();
        // then
        // 이 시점에서는 그룹 정보를 조회하지 않음 = fetch가 lazy라서
        System.out.println("\n\nfoundIdol = " + foundIdol);

        // 요청하면 그룹정보를 가져옴
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }

    @Test
    @DisplayName("순수 SQL로 특정 이름의 아이돌 조회하기")
    void nativeSqlTest() {
        // given
        String sql = """
                SELECT *
                FROM tbl_idol
                WHERE idol_name = ?1
                """;
        // when
        Idol foundIdol = (Idol) em.createNativeQuery(sql, Idol.class)
                .setParameter(1, "쇼타로")
                .getSingleResult();
        // then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }

    @Test
    @DisplayName("스프링 JDBC로 특정 이름의 아이돌 조회하기")
    void jdbcTest() {
        // given
        String sql = """
                SELECT *
                FROM tbl_idol
                WHERE idol_name = ?
                """;
        // when
        Idol foundIdol = jdbcTemplate.queryForObject(sql, (ResultSet rs, int n) -> new Idol(
//                rs.getLong("idol_id")
//                , rs.getString("idol_name")
//                , rs.getInt("age")
//                , null
        ), "성찬");

        Group foundGroup = jdbcTemplate.queryForObject("""
                SELECT *
                FROM tbl_group
                WHERE group_id = ?
//                """, (ResultSet rs, int n) -> new Group(
//                rs.getLong("group_id")
//                , rs.getString("group_name")
//                , null
        ), 1);

        foundIdol.setGroup(foundGroup);

        // then
        System.out.println("foundIdol = " + foundIdol);
        System.out.println("foundGroup = " + foundGroup);
    }


    @Test
    @DisplayName("QueryDsl로 특정 이름의 아이돌 조회하기")
    void queryDslTest() {
        // given
        // JPAQueryFactory factory = new JPAQueryFactory(em);

        // when
        Idol foundIdol = factory
                .selectFrom(idol)
                .where(idol.idolName.eq("리쿠"))
                .fetchOne();

        // then
        System.out.println("foundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }
    
    @Test
    @DisplayName("QueryDsl로 이름 AND 나이로 아이돌 조회하기")
    void searchTest() {
        // given
        String name = "시온";
        int age = 24;

        // when
        Idol foundIdol = factory.selectFrom(/*QIdol*/idol)
                .where(idol.idolName.eq(name)
                        .and(idol.age.eq(age))
                )
                .fetchOne();
        // then
        System.out.println("아이돌 이름 = " + foundIdol.getIdolName());
    }


//        idol.idolName.eq("리즈") // idolName = '리즈'
//        idol.idolName.ne("리즈") // idolName != '리즈'
//        idol.idolName.eq("리즈").not() // idolName != '리즈'
//        idol.idolName.isNotNull() //이름이 is not null
//        idol.age.in(10, 20) // age in (10,20)
//        idol.age.notIn(10, 20) // age not in (10, 20)
//        idol.age.between(10,30) //between 10 and 30
//        idol.age.goe(30) // age >= 30
//        idol.age.gt(30) // age > 30
//        idol.age.loe(30) // age <= 30
//        idol.age.lt(30) // age < 30
//        idol.idolName.like("_김%")  // like _김%
//        idol.idolName.contains("김") // like %김%
//        idol.idolName.startsWith("김") // like 김%
//        idol.idolName.endsWith("김") // like %김

    @Test
    @DisplayName("다양한 조회결과 반환하기")
    void fetchTest() {

        // fetch(): 다중 행 조회
        List<Idol> idolList = factory
                .selectFrom(idol)
                .fetch();

        System.out.println("=========================");
        idolList.forEach(System.out::println);

        // fetchFirst(): 다중 행 조회에서 첫번째 행을 반환
        Idol fetchFirst = factory
                .selectFrom(idol)
                .where(idol.age.loe(22))
                .fetchFirst();

        System.out.println("=========================");
        if(fetchFirst == null) {
            System.out.println("조회된 아이돌이 없습니다.");
        }
        System.out.println("fetchFirst = " + fetchFirst);

        // 단일행 조회시 NPE(널포인트익셉션)에 취약하기 때문에 Optional 을 사용하고 싶을 때는?
        Optional<Idol> fetchOne = Optional.ofNullable(
                factory
                        .selectFrom(idol)
                        .where(idol.idolName.eq("은석"))
                        .fetchOne()
        );

        Group riize = factory.selectFrom(group)
                .where(group.groupName.in("라이즈"))
                .fetchOne();

        Idol foundIdol = fetchOne.orElse(new Idol("은석", 25, riize));
        idolRepository.save(foundIdol);

        List<Idol> idolList1 = factory.selectFrom(idol)
                .where(idol.group.groupName.eq(foundIdol.getGroup().getGroupName()))
                .fetch();

        idolList1.forEach(idol1 -> System.out.println(idol1.getIdolName()));

    }

    @Test
    @DisplayName("나이가 24세 이상인 아이돌을 조회하세요")
    void goeTest() {
        // given
        // when
        List<Idol> idolList = factory
                .selectFrom(idol)
                .where(idol.age.goe(24))
                .fetch();
        // then
        idolList.forEach(System.out::println);
    }

    @Test
    @DisplayName("이름에 '김'이라는 문자열이 포함된 아이돌을 조회하세요.")
    void likeTest() {
        // given
        String subString = "김";
        // when
        List<Idol> idolList = factory
                .selectFrom(idol)
                .where(idol.idolName.contains(subString))
                .fetch();
        // then
        for (Idol foundIdol : idolList) {
            if(foundIdol == null) System.out.println("찾으시는 아이돌이 없습니다.");
            System.out.println(foundIdol.getIdolName());
        }

    }

    @Test
    @DisplayName("나이가 20세에서 25세 사이인 아이돌 조회")
    void ageTest() {
        List<Idol> idolList = factory
                .selectFrom(idol)
                .where(idol.age.between(20, 25))
                .fetch();

        idolList.forEach(idol -> System.out.println(idol.getIdolName()));
    }

    @Test
    @DisplayName("라이즈 그룹에 속한 아이돌 조회")
    void testGroupEquals() {
        // given
        String groupName = "라이즈";

        // when
        List<Idol> idolList = factory
                .selectFrom(idol)
                .where(idol.group.groupName.eq(groupName))
                .fetch();
        // then
        idolList.forEach(System.out::println);
    }

}