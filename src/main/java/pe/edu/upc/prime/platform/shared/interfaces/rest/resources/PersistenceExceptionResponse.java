package pe.edu.upc.prime.platform.shared.interfaces.rest.resources;

/**
 * Response for PersistenceException
 *
 * @param status Status code
 * @param error Error message
 * @param message Detailed message
 */
public record PersistenceExceptionResponse(
        int status, String error, String message) {
}
