package org.example.exception;

public class FilmAlreadyLikedException extends RuntimeException {
    public FilmAlreadyLikedException(String message) {
        super(message);
    }
}
