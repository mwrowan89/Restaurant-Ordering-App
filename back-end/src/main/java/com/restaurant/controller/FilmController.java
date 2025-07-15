package com.restaurant.controller;

import com.restaurant.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurant.entity.Film;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FilmController {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        filmRepository.findAll().forEach(films::add);
        return films;
    }
}
