package pe.edu.upc.prime.platform.iam.interfaces.rest.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.upc.prime.platform.iam.domain.exceptions.UserNotfoundException;
import pe.edu.upc.prime.platform.shared.interfaces.rest.resources.NotFoundExceptionResponse;

/**
 * Global exception handler for User-related exceptions in the IAM module.
 */
@RestControllerAdvice(basePackages = "pe.edu.upc.prime.platform.iam")
public class UserExceptionHandler {

    /**
     * Handle UserNotfoundException and return a standardized NotFoundExceptionResponse.
     *
     * @param ex the UserNotfoundException instance
     * @return ResponseEntity containing NotFoundExceptionResponse with 404 status
     */
    @ExceptionHandler(UserNotfoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<NotFoundExceptionResponse> handleUserNotfoundException(
            UserNotfoundException ex) {
        var response = new NotFoundExceptionResponse(
                HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
