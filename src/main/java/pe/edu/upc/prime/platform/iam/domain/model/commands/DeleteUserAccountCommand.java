package pe.edu.upc.prime.platform.iam.domain.model.commands;

/**
 * Command to delete a user account by its ID.
 *
 * @param idUserAccount the ID of the user account to be deleted
 */
public record DeleteUserAccountCommand(String idUserAccount) {
}
