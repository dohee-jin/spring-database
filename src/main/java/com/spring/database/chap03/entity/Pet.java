package com.spring.database.chap03.entity;

/*
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         pet_name VARCHAR(50),
                         pet_age INT,
                         injection BOOLEAN
 */
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Pet {

    private Long id;
    private String petName;
    private int petAge;
    private boolean injection;

}
