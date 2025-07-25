package com.spring.database.chap03;

import com.spring.database.chap03.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// mybatis 는 repository 를 클래스가 아닌 인터페이스로 생성
@Mapper  // bean 등록
public interface PetMapper {

    // CRUD 를 명세
    // CREATE
    boolean save(Pet pet);

    // READ - SINGLE MATCHING
    Pet findById(Long id);

    // READ - Multiple MATCHING
    List<Pet> findAll();

    // UPDATE
    boolean update(Pet pet);

    // DELETE
    boolean delete(Long id);

    // READ - COUNT
    int petCount();

}
