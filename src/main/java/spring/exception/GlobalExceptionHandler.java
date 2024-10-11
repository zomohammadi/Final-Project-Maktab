package spring.exception;

import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.dto.FieldErrorDto;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Map<String, Object> body = new LinkedHashMap<>();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        body.clear();
        //  Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST);

        //Get all errors
        List<FieldErrorDto> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        body.put("errors", errors);
        return new ResponseEntity<>(
                body, headers, status
        );
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExistsException(EntityExistsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);// HTTP 409 Conflict
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);// HTTP 404 NOT_FOUND
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        body.clear();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }


}