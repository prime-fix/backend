package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to get a user account by user ID.
 *
 * @param userId the ID of the user
 */
public record GetUserAccountByUserIdQuery(Long userId) {
}
