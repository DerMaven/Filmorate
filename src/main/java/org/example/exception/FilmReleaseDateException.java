package org.example.exception;

public class FilmReleaseDateException extends RuntimeException {
    public FilmReleaseDateException(String message) {
        super(message);
    }
}
