package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Command to update a membership.
 *
 * @param membershipId the identifier of the membership to be updated
 * @param membershipDescription the new description of the membership to be updated
 * @param started the new start date of the membership to be updated
 * @param over the new end date of the membership to be updated
 */
public record UpdateMembershipCommand(Long membershipId, MembershipDescription membershipDescription,
                                      LocalDate started, LocalDate over) {
    public UpdateMembershipCommand {
        Objects.requireNonNull(membershipId, "[UpdateMembershipCommand] id membership must not be null");
        Objects.requireNonNull(membershipDescription, "[UpdateMembershipCommand] membership description must not be null");
        Objects.requireNonNull(started, "[UpdateMembershipCommand] started date must not be null");
        Objects.requireNonNull(over, "[UpdateMembershipCommand] over date must not be null");
    }
}
