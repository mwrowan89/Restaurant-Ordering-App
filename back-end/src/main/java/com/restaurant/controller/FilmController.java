package com.restaurant.controller;

import com.restaurant.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.restaurant.entity.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FilmController {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

    @Autowired
    private FilmRepository filmRepository;

    // Retrieve all films
    @GetMapping("/films")
    public ResponseEntity<?> getAllFilms() {
        try {
            List<Film> films = new ArrayList<>();
            filmRepository.findAll().forEach(films::add);
            return ResponseEntity.ok(films);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving films.");
        }
    }

    // Add a new film
    @PostMapping("/films")
    public ResponseEntity<?> addFilm(@RequestBody Film film) {
        try {
            Film savedFilm = filmRepository.save(film);
            return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while saving the film.");
        }
    }

    // Get a film by ID
    @GetMapping("/films/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable("id") String id) {
        try {
            Optional<Film> film = filmRepository.findById(id);
            if (film.isPresent()) {
                return ResponseEntity.ok(film.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Film with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the film.");
        }
    }

    // Update a film by ID
    @PutMapping("/films/{id}")
    public ResponseEntity<?> updateFilm(@PathVariable("id") String id, @RequestBody Film updatedFilm) {
        try {
            Optional<Film> existingFilmOpt = filmRepository.findById(id);
            if (existingFilmOpt.isPresent()) {
                Film existingFilm = existingFilmOpt.get();

                existingFilm.setTitle(updatedFilm.getTitle());


                existingFilm.setHomepage(updatedFilm.getHomepage());
                existingFilm.setOverview(updatedFilm.getOverview());
                existingFilm.setPosterPath(updatedFilm.getPosterPath());
                existingFilm.setRuntime(updatedFilm.getRuntime());
                existingFilm.setTagline(updatedFilm.getTagline());
                existingFilm.setPopularity(updatedFilm.getPopularity());
                existingFilm.setImdbId(updatedFilm.getImdbId());
                existingFilm.setVoteAverage(updatedFilm.getVoteAverage());
                existingFilm.setVoteCount(updatedFilm.getVoteCount());

                Film savedFilm = filmRepository.save(existingFilm);
                return ResponseEntity.ok(savedFilm);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Film with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the film.");
        }
    }


    // Delete a film by ID
    @DeleteMapping("/films/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable("id") String id) {
        try {
            if (filmRepository.existsById(id)) {
                filmRepository.deleteById(id);
                return ResponseEntity.ok("Film with ID " + id + " has been deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Film with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the film.");
        }
    }



}
