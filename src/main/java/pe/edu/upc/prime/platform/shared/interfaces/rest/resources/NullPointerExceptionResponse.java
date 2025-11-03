package pe.edu.upc.prime.platform.shared.interfaces.rest.resources;

/**
 * Response for NullPointerException handling.
 *
 * @param status Status code
 * @param error Error description
 * @param message Message detail
 */
public record NullPointerExceptionResponse(
        int status, String error, String message) {
}
