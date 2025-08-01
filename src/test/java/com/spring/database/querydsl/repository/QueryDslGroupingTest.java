package com.spring.database.querydsl.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.dto.GroupAverageAge;
import com.spring.database.querydsl.entity.Group;
import com.spring.database.querydsl.entity.Idol;
import com.spring.database.querydsl.entity.QIdol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.spring.database.querydsl.entity.QIdol.idol;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class QueryDslGroupingTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    JPAQueryFactory factory;

    @BeforeEach
    void testInsertData() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");
        Group bts = new Group("방탄소년단");
        Group newjeans = new Group("newjeans");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);
        groupRepository.save(bts);
        groupRepository.save(newjeans);

        Idol idol1 = new Idol("김채원", 24, "여", leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, "여", leSserafim);
        Idol idol3 = new Idol("가을", 22, "여", ive);
        Idol idol4 = new Idol("리즈", 20, "여", ive);
        Idol idol5 = new Idol("장원영", 20, "여", ive);
        Idol idol6 = new Idol("안유진", 21, "여", ive);
        Idol idol7 = new Idol("카즈하", 21, "여", leSserafim);
        Idol idol8 = new Idol("RM", 29, "남", bts);
        Idol idol9 = new Idol("정국", 26, "남", bts);
        Idol idol10 = new Idol("해린", 18, "여", newjeans);
        Idol idol11 = new Idol("혜인", 16, "여", newjeans);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);
        idolRepository.save(idol6);
        idolRepository.save(idol7);
        idolRepository.save(idol8);
        idolRepository.save(idol9);
        idolRepository.save(idol10);
        idolRepository.save(idol11);
    }

    @Test
    @DisplayName("SELECT 절에서 원하는 컬럼만 조회하는 방법")
    void tupleTest() {
        // given

        // when
        List<Tuple> idolList = factory.select(idol.idolName, idol.age)
                .from(idol)
                .fetch();

        for (Tuple tuple : idolList) {
            String name = tuple.get(idol.idolName);
            Integer age = tuple.get(idol.age);

            System.out.printf("\n\n이름: %s, 나이: %d\n", name, age);
        }

        // then
    }

    @Test
    @DisplayName("전체 그룹화하기 - group by 없이 집계함수를 사용하는 것")
    void groupByTest() {
        // given

        // when
        Integer sumAge = factory
                .select(idol.age.sum()) // sum(age)
                .from(idol)
                .fetchOne();
        // then
        System.out.println("sumAge = " + sumAge);
    }

    @Test
    @DisplayName("그룹별 인원수 세기")
    void groupByCountTest() {
        /*
            SELECT g.group_name, COUNT(*)
            FROM tbl_idol i
            JOIN tbl_group g
            WHERE i.group_id = g.group_id
            GROUP BY i.group_id;
         */
        // given

        // when
        List<Tuple> idolCounts = factory.select(idol.group.groupName, idol.count())
                .from(idol)
                .groupBy(idol.group.id)
                .fetch();

        // then
        for (Tuple tuple : idolCounts) {
            String groupName = tuple.get(idol.group.groupName);
            Long count = tuple.get(idol.count());

            System.out.printf("\n\n그룹 명: %s, 인원수: %d\n", groupName, count);
        }
    }
    
    @Test
    @DisplayName("아이돌의 성별 인원수를 확인한다")
    void groupByGenderTest() {
        // given
        /*
            SELECT gender, COUNT(*)
            FROM tbl_idol
            GROUP BY gender
            ;
         */
        // when
        List<Tuple> tuples = factory
                .select(idol.gender, idol.count())
                .from(idol)
                .groupBy(idol.gender)
                .fetch();
        // then
        tuples.forEach(tuple -> {
            String gender = tuple.get(idol.gender);
            Long count = tuple.get(idol.count());

            System.out.printf("성별: %s, 인원수: %d\n", gender, count);
        });
    }

    @Test
    @DisplayName("그룹별로 그룹명과 평균 나이를 조회")
    void groupByAvgAgeTest() {
        // given

        // when
        List<Tuple> tuples = factory
                .select(idol.group.groupName, idol.age.avg())
                .from(idol)
                .groupBy(idol.group)
                .having(idol.age.avg().between(20, 25))
                .fetch();
        // then
        tuples.forEach(tuple -> {
            String groupName = tuple.get(idol.group.groupName);
            Double avgAge = tuple.get(idol.age.avg());

            System.out.printf("그룹명: %s, 평균나이: %f\n", groupName, avgAge);
        });
    }

    @Test
    @DisplayName("튜플대신 dto를 사용해서 조회데이터 매핑하기 ver1")
    void groupAvgDtoTest1() {
        // given

        // when
        List<GroupAverageAge> results = factory
                .select(idol.group.groupName, idol.age.avg())
                .from(idol)
                .groupBy(idol.group)
                .having(idol.age.avg().between(20, 25))
                .fetch()
                .stream()
                .map(tuple -> GroupAverageAge.from(tuple))
                .collect(Collectors.toUnmodifiableList());
        // then
        for (GroupAverageAge result : results) {
            String groupName = result.getGroupName();
            double averageAge = result.getAverageAge();

            System.out.printf("그룹명: %s, 평균나이: %.2f\n", groupName, averageAge);
        }
    }

    @Test
    @DisplayName("튜플대신 dto를 사용해서 조회데이터 매핑하기 ver2")
    void groupAvgDtoTest2() {
        // given

        // when
        List<GroupAverageAge> results = factory
                .select(
                        Projections.constructor(
                                // 1. 사용할 DTO를 명시
                                GroupAverageAge.class,
                                // 2. 파라미터를 전달
                                idol.group.groupName,
                                idol.age.avg()
                        )
                )
                .from(idol)
                .groupBy(idol.group)
                .having(idol.age.avg().between(20, 25))
                .fetch();

        // then
        for (GroupAverageAge result : results) {
            String groupName = result.getGroupName();
            double averageAge = result.getAverageAge();

            System.out.printf("그룹명: %s, 평균나이: %.2f\n", groupName, averageAge);
        }
    }

    @Test
    @DisplayName("연령대별 아이돌 수 조회")
    void testAgeGroupBy() {
        // given
        // 연령대를 기준으로 그룹화하고, 각 연령대에 속한 아이돌 수를 조회
        NumberExpression<Integer> ageGroup = new CaseBuilder()
                .when(idol.age.between(10, 19)).then(10)
                .when(idol.age.between(20, 29)).then(20)
                .when(idol.age.between(30, 39)).then(30)
                .otherwise(0);

        // when
        List<Tuple> result = factory
                .select(ageGroup, idol.count())
                .from(idol)
                .groupBy(ageGroup)
                .having(idol.count().goe(2))
                .fetch();

        // then
        assertFalse(result.isEmpty());
        for (Tuple tuple : result) {
            int ageGroupValue = tuple.get(ageGroup);
            long count = tuple.get(idol.count());

            System.out.println("\n\nAge Group: " + ageGroupValue + "대, Count: " + count);
        }
    }

}
