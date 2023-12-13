package org.alexkolo.rest.controller;

import org.alexkolo.rest.exception.UpdateStateException;
import org.alexkolo.rest.model.dto.ErrorResponse;
import org.alexkolo.rest.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException ex) {
        log.error("Error of EntityNotFound", ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(UpdateStateException.class)
    public ResponseEntity<ErrorResponse> lateUpdate(UpdateStateException ex) {
        log.error("Error of LateUpdateState", ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValid(MethodArgumentNotValidException ex) {
        log.error("Error of ArgumentNotValid", ex);

        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(String.join("; ", errorMessages)));
    }

}
