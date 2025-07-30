package com.spring.database.practice.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString(exclude = {"series"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "epi_id")
    private Long id;

    @Column(name = "epi_no", nullable = false)
    private int episodeNumber;

    @Column(name = "epi_title")
    private String title;

    @Column(name = "duration")
    private int duration;

    @Column(name = "description")
    private String description;

    @Column(name = "view_count")
    private int viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;
}
