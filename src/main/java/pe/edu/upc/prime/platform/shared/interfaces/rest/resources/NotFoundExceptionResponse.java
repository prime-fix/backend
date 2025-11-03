package pe.edu.upc.prime.platform.shared.interfaces.rest.resources;

/**
 * Response for NotFoundException
 *
 * @param status Status code
 * @param error Error message
 * @param message Detailed message
 */
public record NotFoundExceptionResponse(
        int status, String error, String message) {
}
