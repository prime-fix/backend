package pe.edu.upc.prime.platform.iam.domain.model.queries;

/**
 * This class represents the query to check if a user exists by its id.
 *
 * @param idUser the id of the user.
 */
public record ExistsUserByIdQuery(String idUser) {
}
