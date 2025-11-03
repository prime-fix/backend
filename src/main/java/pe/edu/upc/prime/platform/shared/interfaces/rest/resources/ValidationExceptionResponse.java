package pe.edu.upc.prime.platform.shared.interfaces.rest.resources;

import java.util.Map;

/**
 * Response for ValidationException
 *
 * @param status Status code
 * @param error Error message
 * @param message Detailed message
 * @param fieldErrors Field-specific validation errors
 */
public record ValidationExceptionResponse(
        int status, String error, String message, Map<String, String> fieldErrors) {
}
