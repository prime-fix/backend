package pe.edu.upc.prime.platform.data.collection.interfaces.rest.handlers;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.BadRequestResponse;

import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for visit-related exceptions.
 */
@RestControllerAdvice(basePackages = "pe.ed.upc.prime.platform.data.collection")
public class VisitExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BadRequestResponse> handleDateTimeParseException(
            DateTimeParseException ex) {
        Map<String,String> fieldErrors=new LinkedHashMap<>();
        fieldErrors.put("dateTime", "The date string provided is invalid");

        var response = new BadRequestResponse(
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                "Date parsing failed", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BadRequestResponse> handleJsonParseError(
            HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            Map<String, String> fieldErrors = new LinkedHashMap<>();
            fieldErrors.put("birthDate", "Invalid value '" + ife.getValue().toString()
                    + "' for LocalDate. Expected format (YYYY-MM-DD) ");

            var response = new BadRequestResponse(
                    HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                    "Date parsing failed", fieldErrors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
