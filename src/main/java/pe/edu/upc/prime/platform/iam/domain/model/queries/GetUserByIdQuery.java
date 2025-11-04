package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * Query to get a user by its ID.
 *
 * @param idUser the ID of the user to retrieve
 */
public record GetUserByIdQuery(String idUser) {
}
