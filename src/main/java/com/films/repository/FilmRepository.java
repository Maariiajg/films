package com.films.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.films.entity.Film;
import com.films.entity.Genero;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    
    List<Film> findByGenero(Genero genero);

    List<Film> findByFechaEstrenoAfter(LocalDate fecha);

    List<Film> findByFechaEstrenoBetween(LocalDate start, LocalDate end);

    List<Film> findByPuntuacionBetween(double min, double max);
}
