package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Response for a membership.
 *
 * @param idMembership the identifier of the membership
 * @param description the description of the membership
 * @param started the start date of the membership
 * @param over the end date of the membership
 */
public record MembershipResponse(
        @JsonProperty("id_membership") String idMembership,
        String description,
        LocalDate started,
        LocalDate over) {
}
