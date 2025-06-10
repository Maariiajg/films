package com.films.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.films.entity.Film;
import com.films.entity.Genero;
import com.films.service.FilmService;
import com.films.service.exceptions.FilmException;
import com.films.service.exceptions.FilmNotFoundException;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService service) {
        this.filmService = service;
    }

    // Obtener todas las películas
    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    // Obtener una película por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(this.filmService.findById(id));
		} catch (FilmNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

    // Crear una nueva película
    @PostMapping
    public ResponseEntity<Film> create(@RequestBody Film film) {
        return ResponseEntity.ok(filmService.create(film));
    }

    // Modificar una película existente (excepto la puntuación)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Film film) {
		try {
			return ResponseEntity.ok(this.filmService.update(id, film));
		} catch (FilmNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (FilmException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

    // Borrar una película por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			this.filmService.deleteById(id);
			return ResponseEntity.ok("La pelicula con ID("+ id +") ha sido borrada correctamente. " );
		} catch (FilmNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}

	}
    
    //Obtener peliculas de ACCION
    @GetMapping("/ACCION")
	public ResponseEntity<?> peliculasAccion(){
		return ResponseEntity.status(HttpStatus.OK).body(this.filmService.peliculasAccion());
	}
    
  //Obtener peliculas de COMEDIA
    @GetMapping("/COMEDIA")
	public ResponseEntity<?> peliculasComedia(){
		return ResponseEntity.status(HttpStatus.OK).body(this.filmService.peliculasComedia());
	}
    
    //Obtener peliculas de DRAMA
    @GetMapping("/DRAMA")
	public ResponseEntity<?> peliculasDrama(){
		return ResponseEntity.status(HttpStatus.OK).body(this.filmService.peliculasDrama());
	}

    // Obtener películas por género (ACCION, COMEDIA, DRAMA, OTRO) este obtiene formato lista con todos los datos
    @GetMapping("/genero/{genero}")
    public List<Film> getByGenero(@PathVariable Genero genero) {
        return filmService.getByGenero(genero);
    }

    // Obtener películas que aún no se han estrenado (fecha_estreno > hoy)
    @GetMapping("/no-estrenadas")
    public List<Film> getNoEstrenadas() {
        return filmService.getNoEstrenadas();
    }

    // Obtener películas estrenadas en el año actual
    @GetMapping("/estrenadas-este-anio")
    public List<Film> getEstrenadasThisYear() {
        return filmService.getEstrenadasThisYear();
    }

    // Obtener películas estrenadas en un año específico
    @GetMapping("/estrenadas-en/{anio}")
    public List<Film> getEstrenadaInYear(@PathVariable int anio) {
        return filmService.getEstrenadaInYear(anio);
    }

    // Obtener películas cuya puntuación esté en un rango determinado
    @GetMapping("/puntuacion")
    public List<Film> getRangoPuntuacion(@RequestParam double min, @RequestParam double max) {
        return filmService.getRangoPuntuacion(min, max);
    }

    // Puntuar una película (solo desde aquí se puede cambiar la puntuación)
    @PostMapping("/{id}/puntuar")
    public ResponseEntity<Film> rateFilm(@PathVariable int id, @RequestParam double puntuacion) {
        return ResponseEntity.ok(filmService.rateFilm(id, puntuacion));
    }
}
