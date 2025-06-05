package com.films.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.films.entity.Film;
import com.films.entity.Genero;
import com.films.service.FilmService;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    // Obtener todas las películas
    @GetMapping
    public List<Film> getAll() {
        return service.getAll();
    }

    // Obtener una película por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable int id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva película
    @PostMapping
    public ResponseEntity<Film> create(@RequestBody Film film) {
        return ResponseEntity.ok(service.create(film));
    }

    // Modificar una película existente (excepto la puntuación)
    @PutMapping("/{id}")
    public ResponseEntity<Film> update(@PathVariable int id, @RequestBody Film film) {
        return ResponseEntity.ok(service.update(id, film));
    }

    // Borrar una película por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener películas por género (ACCION, COMEDIA, DRAMA, OTRO)
    @GetMapping("/genero/{genero}")
    public List<Film> getByGenero(@PathVariable Genero genero) {
        return service.getByGenero(genero);
    }

    // Obtener películas que aún no se han estrenado (fecha_estreno > hoy)
    @GetMapping("/no-estrenadas")
    public List<Film> getNotReleased() {
        return service.getNotReleased();
    }

    // Obtener películas estrenadas en el año actual
    @GetMapping("/estrenadas-este-anio")
    public List<Film> getReleasedThisYear() {
        return service.getReleasedThisYear();
    }

    // Obtener películas estrenadas en un año específico
    @GetMapping("/estrenadas-en/{anio}")
    public List<Film> getReleasedInYear(@PathVariable int anio) {
        return service.getReleasedInYear(anio);
    }

    // Obtener películas cuya puntuación esté en un rango determinado
    @GetMapping("/puntuacion")
    public List<Film> getByRatingRange(@RequestParam double min, @RequestParam double max) {
        return service.getByRatingRange(min, max);
    }

    // Puntuar una película (solo desde aquí se puede cambiar la puntuación)
    @PostMapping("/{id}/puntuar")
    public ResponseEntity<Film> rateFilm(@PathVariable int id, @RequestParam double puntuacion) {
        return ResponseEntity.ok(service.rateFilm(id, puntuacion));
    }
}
