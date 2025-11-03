package pe.edu.upc.prime.platform.shared.interfaces.rest.resources;

/**
 * Response for IllegalArgumentException
 *
 * @param status Status code
 * @param error Error message
 * @param message Detailed message
 */
public record IllegalArgumentExceptionResponse(
        int status, String error, String message) {
}
