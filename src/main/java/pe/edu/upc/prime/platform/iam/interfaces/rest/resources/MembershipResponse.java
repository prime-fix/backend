package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * Response for a membership.
 *
 * @param id the identifier of the membership
 * @param description the description of the membership
 * @param started the start date of the membership
 * @param over the end date of the membership
 */
public record MembershipResponse(
        Long id,
        String description,
        LocalDate started,
        LocalDate over) {
}
