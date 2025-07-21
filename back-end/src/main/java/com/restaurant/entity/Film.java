package com.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "films")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "overview", length = 1500)
    private String overview;

    @Column(name = "posterpath")
    private String posterPath;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "tagline")
    private String tagline;

    @Column(name = "popularity")
    private Double popularity;

    @Column(name = "imdbid")
    private String imdbId;

    @Column(name = "voteaverage")
    private Double voteAverage;

    @Column(name = "votecount")
    private Integer voteCount;
}