package org.example.exception;

public class FilmAlreadyExistsException extends RuntimeException {
    public FilmAlreadyExistsException(String message) {
        super(message);
    }
}
