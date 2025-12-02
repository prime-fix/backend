package pe.edu.upc.prime.platform.iam.domain.model.commands;

import java.util.Objects;

/**
 * Command to delete a membership.
 *
 * @param membershipId the id of the membership to be deleted
 */
public record DeleteMembershipCommand(Long membershipId) {
    public DeleteMembershipCommand {
       Objects.requireNonNull(membershipId, "[DeleteMembershipCommand] Membership ID must not be null");
    }
}
