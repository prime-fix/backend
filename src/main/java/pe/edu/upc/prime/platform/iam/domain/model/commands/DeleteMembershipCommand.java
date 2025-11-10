package pe.edu.upc.prime.platform.iam.domain.model.commands;

/**
 * Command to delete a membership.
 *
 * @param idMembership the id of the membership to be deleted
 */
public record DeleteMembershipCommand(String idMembership) {
}
