package com.spring.database.practice.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(exclude = {"episode"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "series_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "release_year")
    private LocalDate releaseYear;

    @Column(name = "director")
    private String director;

    @OneToMany(mappedBy = "series")
    private List<Episode> episode = new ArrayList<>();


    public enum Genre {
        DRAMA("드라마"), ACTION("액션"), COMEDY("코메디");

        private final String koreanName;

        Genre(String koreanName) {
            this.koreanName = koreanName;
        }

        public String getKoreanName() {
            return this.koreanName;
        }
    }
}
