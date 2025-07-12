package com.restaurant.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @Column(name = "id")
    private String filmId;

    @Column(nullable = false)
    private String title;

    @Column
    private String homepage;

    @Column
    private LocalDate releaseDate;

    @Column
    private String overview;

    @Column
    private String posterPath;

    @Column
    private Integer runtime;

    @Column
    private String tagline;

    @Column
    private Double popularity;

    @Column(unique = true)
    private String imdbId;

    @Column
    private Double voteAverage;

    @Column
    private Integer voteCount;

    // Getters and Setters
    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String id) {
        this.filmId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}