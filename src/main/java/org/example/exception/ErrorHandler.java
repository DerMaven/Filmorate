package org.example.exception;

import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmAlreadyExists(FilmAlreadyExistsException e) {
        return new ErrorResponse("Ошибка существующего поста", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmAlreadyLiked(FilmAlreadyLikedException e) {
        return new ErrorResponse("Ошибка поставленного лайка на фильм", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotFound(FilmNotFoundException e) {
        return new ErrorResponse("Ошибка поиска фильма", e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotLiked(FilmNotLikedException e) {
        return new ErrorResponse("Ошибка поиска лайка фильма", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmReleaseDate(FilmReleaseDateException e) {
        return new ErrorResponse("Ошибка неверной даты выпуска: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRegistration(ValidationException e) {
        return new ErrorResponse("Ошибка регистрации: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleSameID(SameIDException e) {
        return new ErrorResponse("Ошибка одинакового ID: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUpdate(UpdateException e) {
        return new ErrorResponse("Ошибка обновления сущности: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserAlreadyExists(UserAlreadyExistsException e) {
        return new ErrorResponse("Ошибка существующего пользователя: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException e) {
        return new ErrorResponse("Ошибка поиска пользователя: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Throwable t) {
        return new ErrorResponse("Возникла внезапная ошибка", t.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return new ErrorResponse("Ошибка валидации данных", e.getMessage());
    }

    @Getter
    @RequiredArgsConstructor
    public static class ErrorResponse {
        private final String error;
        private final String description;
    }
}
