package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to get a user account by its ID.
 *
 * @param userAccountId the ID of the user account to retrieve
 */
public record GetUserAccountByIdQuery(Long userAccountId) {
}
