package org.example.exception;

public class FilmNotLikedException extends RuntimeException {
    public FilmNotLikedException(String message) {
        super(message);
    }
}
