 package com.restaurant.entity;

 import jakarta.persistence.*;
 import java.time.LocalDate;

 @Entity
 @Table(name = "films")
 public class Film {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id", nullable = false)
 private Long id;

 @Column(name = "title", nullable = false)
 private String title;

 @Column(name = "homepage")
 private String homepage;

// @Column()
// private LocalDate releaseDate;

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

 // Getters and Setters
 public Long getId() {
 return id;
 }

 public void setId(Long id) {
 this.id = id;
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

// public LocalDate getReleaseDate() {
// return releaseDate;
// }
//
// public void setReleaseDate(LocalDate releaseDate) {
// this.releaseDate = releaseDate;
// }

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