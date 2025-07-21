package com.restaurant.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.restaurant.entity.Film;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use the real database
public class FilmRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FilmRepository filmRepository;

    @Test
    @DisplayName("Should save and find film by ID")
    void testSaveAndFindById() {
        // Arrange
        Film film = new Film();
        film.setTitle("Test Film");
        film.setHomepage("http://example.com/film");
        film.setOverview("This is a test film description");
        film.setPosterPath("/images/poster.jpg");
        film.setRuntime(120);
        film.setTagline("A great test film");
        film.setPopularity(7.5);
        film.setImdbId("tt1234567");
        film.setVoteAverage(8.2);
        film.setVoteCount(1000);
        
        // Act
        Film savedFilm = entityManager.persistAndFlush(film);
        Optional<Film> foundFilm = filmRepository.findById(savedFilm.getId().toString());
        
        // Assert
        assertThat(foundFilm).isPresent();
        assertThat(foundFilm.get()).usingRecursiveComparison().isEqualTo(film);
    }
    
    @Test
    @DisplayName("Should update a film")
    void testUpdateFilm() {
        // Arrange
        Film film = new Film();
        film.setTitle("Original Title");
        film.setOverview("Original overview");
        film.setRuntime(120);
        film.setVoteAverage(7.0);
        Film savedFilm = entityManager.persistAndFlush(film);
        
        // Act
        Film filmToUpdate = filmRepository.findById(savedFilm.getId().toString()).get();
        filmToUpdate.setTitle("Updated Title");
        filmToUpdate.setRuntime(150);
        filmRepository.save(filmToUpdate);
        
        // Assert
        Film updatedFilm = filmRepository.findById(savedFilm.getId().toString()).get();
        assertEquals("Updated Title", updatedFilm.getTitle(), "Film title should be updated");
        assertEquals(150, updatedFilm.getRuntime(), "Film runtime should be updated");
    }
    
    @Test
    @DisplayName("Should delete a film")
    void testDeleteFilm() {
        // Arrange
        Film film = new Film();
        film.setTitle("Film to Delete");
        film.setOverview("This film will be deleted");
        Film savedFilm = entityManager.persistAndFlush(film);
        
        // Act
        filmRepository.deleteById(savedFilm.getId().toString());
        Optional<Film> foundFilm = filmRepository.findById(savedFilm.getId().toString());
        
        // Assert
        assertThat(foundFilm).isEmpty();
    }
    
    @Test
    @DisplayName("Should check if film exists by ID")
    void testExistsById() {
        // Arrange
        Film film = new Film();
        film.setTitle("Existing Film");
        Film savedFilm = entityManager.persistAndFlush(film);
        
        // Act & Assert
        assertThat(filmRepository.existsById(savedFilm.getId().toString())).isTrue();
    }
}
