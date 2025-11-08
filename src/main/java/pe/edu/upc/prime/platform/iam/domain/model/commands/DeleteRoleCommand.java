package pe.edu.upc.prime.platform.iam.domain.model.commands;

/**
 * Command to delete an existing role.
 *
 * @param idRole the identifier of the role to be deleted
 */
public record DeleteRoleCommand(String idRole) {
}
