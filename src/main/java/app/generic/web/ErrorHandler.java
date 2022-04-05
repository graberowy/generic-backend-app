package app.generic.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<?> handleException(IllegalArgumentException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<?> handleException(RuntimeException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    ResponseEntity<?> handleException(UnsupportedOperationException e) {
        Error error = new Error();
        error.setMessage("Service Unavailable");
        error.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }



}
