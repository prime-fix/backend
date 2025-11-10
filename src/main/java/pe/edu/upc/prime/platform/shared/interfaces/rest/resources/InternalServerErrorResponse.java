package pe.edu.upc.prime.platform.shared.interfaces.rest.resources;

/**
 * Internal Server Error Response DTO.
 *
 * @param status the HTTP status code
 * @param error the error reason phrase
 * @param message the error message
 */
public record InternalServerErrorResponse(
        int status, String error, String message
) {
}
