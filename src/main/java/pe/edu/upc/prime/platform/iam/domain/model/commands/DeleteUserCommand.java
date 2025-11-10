package pe.edu.upc.prime.platform.iam.domain.model.commands;

/**
 * Command to delete a user by its ID.
 *
 * @param idUser the ID of the user to be deleted
 */
public record DeleteUserCommand(String idUser) {
}
