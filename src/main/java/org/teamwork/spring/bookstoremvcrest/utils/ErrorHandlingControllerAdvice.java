package org.teamwork.spring.bookstoremvcrest.utils;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.teamwork.spring.bookstoremvcrest.exceptions.UnexpectedIdException;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler(UnexpectedIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UsedIdError onUsedIdException(UnexpectedIdException e){
        UsedIdError error = new UsedIdError();
        error.setMessage(e.getMessage());
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationConstraintsError onValidationError(ConstraintViolationException e){
        ValidationConstraintsError error = new ValidationConstraintsError();
        error.addViolations(e.getConstraintViolations());
        error.setMessage("The object did not pass the validation! Violated constraints:");
        return error;
    }
}