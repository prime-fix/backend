package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to check the existence of a user by its ID.
 *
 * @param userId The ID of the user to check.
 */
public record ExistsUserByIdQuery(Long userId) {
}
