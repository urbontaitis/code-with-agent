package com.example.testcontainers.film.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface FilmRepository extends JpaRepository<Film, String> {
  Optional<Film> findByTitle(String title);
  Page<Film> findAll(Pageable pageable);
}
