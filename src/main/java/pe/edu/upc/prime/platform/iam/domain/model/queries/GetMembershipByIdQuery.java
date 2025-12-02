package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to get a membership by its ID.
 *
 * @param membershipId the ID of the membership to retrieve
 */
public record GetMembershipByIdQuery(Long membershipId) {
}
