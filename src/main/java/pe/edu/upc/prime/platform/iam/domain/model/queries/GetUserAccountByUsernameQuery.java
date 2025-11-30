package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to get a user account by email.
 *
 * @param username the username of the user account to retrieve
 */
public record GetUserAccountByUsernameQuery(String username) {
}
