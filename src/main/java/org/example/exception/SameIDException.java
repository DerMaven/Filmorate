package org.example.exception;

public class SameIDException extends RuntimeException {
    public SameIDException(String message) {
        super(message);
    }
}
