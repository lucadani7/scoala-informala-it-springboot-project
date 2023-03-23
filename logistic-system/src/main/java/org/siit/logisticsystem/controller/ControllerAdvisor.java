package org.siit.logisticsystem.controller;

import org.siit.logisticsystem.exception.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMismatchException(MethodArgumentTypeMismatchException e) {
        String message = "";
        if (e.getMostSpecificCause() instanceof DateTimeParseException) {
            message = "Date input has a wrong format.";
        } else {
            message = e.getMessage();
        }
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
