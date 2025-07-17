package com.restaurant.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.entity.Film;
import com.restaurant.repository.FilmRepository;

@ExtendWith(MockitoExtension.class)
public class FilmControllerTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmController filmController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Film testFilm;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(filmController).build();
        objectMapper = new ObjectMapper();
        
        // Create a test film
        testFilm = new Film();
        testFilm.setId(1L);
        testFilm.setTitle("Test Film");
        testFilm.setHomepage("http://example.com/film");
        testFilm.setOverview("This is a test film description");
        testFilm.setPosterPath("/images/poster.jpg");
        testFilm.setRuntime(120);
        testFilm.setTagline("A great test film");
        testFilm.setPopularity(7.5);
        testFilm.setImdbId("tt1234567");
        testFilm.setVoteAverage(8.2);
        testFilm.setVoteCount(1000);
    }

    @Test
    @DisplayName("GET /api/films - Should return all films")
    void testGetAllFilms() throws Exception {
        // Arrange
        List<Film> films = new ArrayList<>();
        films.add(testFilm);
        
        when(filmRepository.findAll()).thenReturn(films);

        // Act & Assert
        mockMvc.perform(get("/api/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(testFilm.getTitle())));

        verify(filmRepository).findAll();
    }

    @Test
    @DisplayName("POST /api/films - Should create a new film")
    void testAddFilm() throws Exception {
        // Arrange
        when(filmRepository.save(any(Film.class))).thenReturn(testFilm);

        // Act & Assert
        mockMvc.perform(post("/api/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testFilm)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(testFilm.getTitle())));

        verify(filmRepository).save(any(Film.class));
    }

    @Test
    @DisplayName("GET /api/films/{id} - Should return film by ID")
    void testGetFilmById() throws Exception {
        // Arrange
        when(filmRepository.findById("1")).thenReturn(Optional.of(testFilm));

        // Act & Assert
        mockMvc.perform(get("/api/films/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(testFilm.getTitle())))
                .andExpect(jsonPath("$.runtime", is(testFilm.getRuntime())));

        verify(filmRepository).findById("1");
    }

    @Test
    @DisplayName("GET /api/films/{id} - Should return 404 when film not found")
    void testGetFilmByIdNotFound() throws Exception {
        // Arrange
        when(filmRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/films/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Film with ID 999 not found")));

        verify(filmRepository).findById("999");
    }

    @Test
    @DisplayName("PUT /api/films/{id} - Should update film")
    void testUpdateFilm() throws Exception {
        // Arrange
        Film updatedFilm = testFilm;
        updatedFilm.setTitle("Updated Film Title");
        updatedFilm.setRuntime(150);
        
        when(filmRepository.findById("1")).thenReturn(Optional.of(testFilm));
        when(filmRepository.save(any(Film.class))).thenReturn(updatedFilm);

        // Act & Assert
        mockMvc.perform(put("/api/films/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Film Title")))
                .andExpect(jsonPath("$.runtime", is(150)));

        verify(filmRepository).findById("1");
        verify(filmRepository).save(any(Film.class));
    }

    @Test
    @DisplayName("DELETE /api/films/{id} - Should delete film")
    void testDeleteFilm() throws Exception {
        // Arrange
        when(filmRepository.existsById("1")).thenReturn(true);
        doNothing().when(filmRepository).deleteById("1");

        // Act & Assert
        mockMvc.perform(delete("/api/films/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Film with ID 1 has been deleted")));

        verify(filmRepository).existsById("1");
        verify(filmRepository).deleteById("1");
    }

    @Test
    @DisplayName("DELETE /api/films/{id} - Should return 404 when film to delete not found")
    void testDeleteFilmNotFound() throws Exception {
        // Arrange
        when(filmRepository.existsById("999")).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/films/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Film with ID 999 not found")));

        verify(filmRepository).existsById("999");
        verify(filmRepository, never()).deleteById(anyString());
    }
}
