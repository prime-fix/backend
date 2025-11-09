package pe.edu.upc.prime.platform.payment.service.interfaces.rest.handlers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.upc.prime.platform.payment.service.interfaces.rest.controllers.RatingsController;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.BadRequestResponse;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Exception handler for Rating-related controllers.
 */
@RestControllerAdvice(assignableTypes = {RatingsController.class})
public class RatingExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BadRequestResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {
            Map<String, String> fieldErrors = new LinkedHashMap<>();
            fieldErrors.put("field", "Invalid value '" + ife.getValue() + "'");
            var response = new BadRequestResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.name(),
                    "Invalid input format",
                    fieldErrors
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        var response = new BadRequestResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                "Malformed JSON or invalid request body",
                Map.of("error", ex.getMostSpecificCause().getMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BadRequestResponse> handleIllegalArgument(IllegalArgumentException ex) {
        var response = new BadRequestResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                "Invalid argument: " + ex.getMessage(),
                Map.of("error", ex.getLocalizedMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
