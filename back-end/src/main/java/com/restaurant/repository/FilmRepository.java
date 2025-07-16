package com.restaurant.repository;

import com.restaurant.entity.Film;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, String> {
}
