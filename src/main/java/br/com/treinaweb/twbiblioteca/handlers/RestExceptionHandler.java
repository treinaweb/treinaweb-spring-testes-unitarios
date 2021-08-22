package br.com.treinaweb.twbiblioteca.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.treinaweb.twbiblioteca.dtos.response.ErrorResponse;
import br.com.treinaweb.twbiblioteca.dtos.response.FieldError;
import br.com.treinaweb.twbiblioteca.dtos.response.ValidationErrorResponse;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        var status = HttpStatus.NOT_FOUND;

        var response = new ErrorResponse();
        response.setCodigo(status.value());
        response.setStatus(status.getReasonPhrase());
        response.setTimestamp(LocalDateTime.now());
        response.setCausa(exception.getLocalizedMessage());

        return new ResponseEntity<>(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        var errors = new ArrayList<FieldError>();
        exception.getBindingResult()
            .getFieldErrors()
            .forEach(error -> {
                var fieldError = new FieldError(error.getField(), error.getDefaultMessage());
                errors.add(fieldError);
            });

        var response = new ValidationErrorResponse();
        response.setCodigo(status.value());
        response.setStatus(status.getReasonPhrase());
        response.setTimestamp(LocalDateTime.now());
        response.setCausa("Há erro(s) de validação");
        response.setErrors(errors);

        return new ResponseEntity<>(response, status);
    }

}
