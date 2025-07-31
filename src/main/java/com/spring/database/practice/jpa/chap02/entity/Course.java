package com.spring.database.practice.jpa.chap02.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(exclude = {"enrollment"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "instructor")
    private String instructor;

    @Column(name = "price")
    private int price;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Enrollment> enrollmentList = new ArrayList<>();
}
