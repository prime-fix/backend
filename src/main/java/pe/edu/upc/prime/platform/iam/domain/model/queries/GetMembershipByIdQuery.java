package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to get a membership by its ID.
 *
 * @param idMembership the ID of the membership to retrieve
 */
public record GetMembershipByIdQuery(String idMembership) {
}
