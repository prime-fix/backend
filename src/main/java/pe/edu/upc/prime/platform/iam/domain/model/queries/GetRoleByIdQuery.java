package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to get a role by its ID.
 *
 * @param idRole the ID of the role to retrieve
 */
public record GetRoleByIdQuery(String idRole) {
}
