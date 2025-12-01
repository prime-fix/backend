package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to check the existence of a user account by its ID.
 *
 * @param userAccountId The ID of the user account to check.
 */
public record ExistsUserAccountByIdQuery(Long userAccountId) {
}
