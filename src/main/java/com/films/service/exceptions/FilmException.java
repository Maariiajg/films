package com.films.service.exceptions;

public class FilmException extends RuntimeException {
    public FilmException(String mensaje) {
        super(mensaje);
    }
}
