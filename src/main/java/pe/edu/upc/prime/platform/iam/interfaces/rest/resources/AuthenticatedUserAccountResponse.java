package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

/**
 * Response for authenticated user account
 *
 * @param id the unique identifier of the user account
 * @param username the username of the user account
 * @param token the authentication token
 */
public record AuthenticatedUserAccountResponse(Long id, String username, String token) {
}
