package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

/**
 * Request body for sign-in operation.
 *
 * @param username the username of the user to authenticate
 * @param password the password of the user to authenticate
 */
public record SignInRequest(String username, String password) {
}
