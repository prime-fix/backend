package pe.edu.upc.prime.platform.iam.domain.model.commands;

import pe.edu.upc.prime.platform.iam.domain.model.valueobjects.MembershipDescription;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Command to create a membership.
 *
 * @param idMembership the id of the membership to be created
 * @param membershipDescription the description of the membership to be created
 * @param started the start date of the membership to be created
 * @param over the end date of the membership to be created
 */
public record CreateMembershipCommand(String idMembership, MembershipDescription membershipDescription,
                                      LocalDate started, LocalDate over) {
    public CreateMembershipCommand {
        Objects.requireNonNull(idMembership, "[CreateMembershipCommand] id membership must not be null");
        Objects.requireNonNull(membershipDescription, "[CreateMembershipCommand] membership description must not be null");
        Objects.requireNonNull(started, "[CreateMembershipCommand] started date must not be null");
        Objects.requireNonNull(over, "[CreateMembershipCommand] over date must not be null");
    }
}
