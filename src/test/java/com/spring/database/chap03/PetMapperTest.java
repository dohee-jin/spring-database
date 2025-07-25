package com.spring.database.chap03;

import com.spring.database.chap03.entity.Pet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetMapperTest {

    @Autowired
    PetMapper petMapper;

    @Test
    @DisplayName("save test")
    void saveTest() {
        // given
        Pet newPet = Pet.builder()
                .petName("홍승한")
                .petAge(3)
                .injection(false)
                .build();
        // when
        boolean flag = petMapper.save(newPet);
        // then
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("update test")
    void updateTest() {
        // given
        Pet updatePet = Pet.builder()
                .petName("냥희")
                .petAge(3)
                .injection(true)
                .id(5L)
                .build();
        // when
        boolean flag = petMapper.update(updatePet);
        // then
        Assertions.assertTrue(flag);
    }

    @Test
    @DisplayName("delete test")
    void deleteTest() {
        // given
        Long id = 7L;
        // when
        boolean flag = petMapper.delete(id);
        // then
        Assertions.assertTrue(flag);
        System.out.println("승한이 잘가게!");
    }

    @Test
    @DisplayName("findAll test")
    void findAllTest() {
        // given
        
        // when
        List<Pet> petList = petMapper.findAll();
        // then
        assertNotNull(petList);
        assertEquals(6, petList.size());
        petList.forEach(pet -> System.out.println(pet.getPetName()));
    }

    @Test
    @DisplayName("findOne test")
    void findOneTest() {
        // given
        Long id = 1L;
        // when
        Pet foundPet = petMapper.findById(id);
        // then
        System.out.println(foundPet);
        assertEquals("쇼햄이", foundPet.getPetName());
    }

    @Test
    @DisplayName("count test")
    void countTest() {
        // given

        // when
        int petCount = petMapper.petCount();
        // then
        assertEquals(6, petCount);
    }
}