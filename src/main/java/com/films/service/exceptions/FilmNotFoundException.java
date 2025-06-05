package com.films.service.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException(String mensaje) {
        super(mensaje);
    }
}
