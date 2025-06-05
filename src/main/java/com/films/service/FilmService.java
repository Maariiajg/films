package com.films.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.films.entity.Film;
import com.films.entity.Genero;
import com.films.repository.FilmRepository;
import com.films.service.exceptions.FilmException;
import com.films.service.exceptions.FilmNotFoundException;

@Service
public class FilmService {

	@Autowired
    private FilmRepository filmRepository;


    // Obtener todas las películas
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    // Obtener película por ID
    public Film findById(int id) {
 		if(!this.filmRepository.existsById(id)) {
 			throw new FilmNotFoundException("No existe el juego con ID: " + id);
 		}
 
 		return this.filmRepository.findById(id).get();
 	}

    // Crear película
    public Film create(Film film) {
        if (film.getDuracion() < 60) {
            throw new IllegalArgumentException("La duración debe ser al menos 60 minutos.");
        }
        if (film.getGenero() == null) {
            film.setGenero(Genero.OTRO); //por defecto será OTRO
        }
        film.setPuntuacion(0.0);
        return filmRepository.save(film);
    }

    // Actualizar película sin modificar puntuación
    public Film update(int id, Film film) {
    	if(!this.filmRepository.existsById(id)) {
 	        throw new FilmNotFoundException("No existe el videojuego con ID: " + id);
 	    }
    	if (film.getDuracion() < 60) {
            throw new IllegalArgumentException("La duración debe ser al menos 60 minutos.");
        }
    	
    	Film filmBD = this.findById(film.getId());
    	
    	if (film.getPuntuacion() != filmBD.getPuntuacion()) {
 	        throw new FilmException("No se permite modificar la puntuación.");
 	    }
    	
    	filmBD.setTitulo(film.getTitulo());
    	filmBD.setGenero(film.getGenero() != null ? film.getGenero() : Genero.OTRO);
    	filmBD.setDirector(film.getDirector());
    	filmBD.setFechaEstreno(film.getFechaEstreno());
    	filmBD.setDuracion(film.getDuracion());
        filmBD.setComentarios(film.getComentarios());
        return filmRepository.save(filmBD);
    }

    // Eliminar película por ID
    public void delete(int id) {
        filmRepository.deleteById(id);
    }

    // Obtener películas por género
    public List<Film> getByGenero(Genero genero) {
        return filmRepository.findByGenero(genero);
    }

    // Obtener películas no estrenadas
    public List<Film> getNotReleased() {
        return filmRepository.findByFechaEstrenoAfter(LocalDate.now());
    }

    // Obtener películas estrenadas este año
    public List<Film> getReleasedThisYear() {
        LocalDate now = LocalDate.now();
        return filmRepository.findByFechaEstrenoBetween(LocalDate.of(now.getYear(), 1, 1), LocalDate.of(now.getYear(), 12, 31));
    }

    // Obtener películas estrenadas en un año específico
    public List<Film> getReleasedInYear(int year) {
        return filmRepository.findByFechaEstrenoBetween(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31));
    }

    // Obtener películas en rango de puntuación
    public List<Film> getByRatingRange(double min, double max) {
        return filmRepository.findByPuntuacionBetween(min, max);
    }

    // Puntuar película (calcula nueva media)
    public Film rateFilm(int id, double nuevaPuntuacion) {
        if (nuevaPuntuacion < 1.0 || nuevaPuntuacion > 10.0) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1.0 y 10.0");
        }
        Film film = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Película no encontrada"));
        double puntuacionActual = film.getPuntuacion();
        if (puntuacionActual == 0.0) {
            film.setPuntuacion(nuevaPuntuacion);
        } else {
            film.setPuntuacion((puntuacionActual + nuevaPuntuacion) / 2);
        }
        return filmRepository.save(film);
    }
}
