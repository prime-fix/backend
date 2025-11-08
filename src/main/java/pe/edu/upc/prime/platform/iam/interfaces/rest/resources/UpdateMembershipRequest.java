package pe.edu.upc.prime.platform.iam.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.shared.utils.Util;

import java.time.LocalDate;

/**
 * Request to update a membership.
 *
 * @param description the description of the membership to be updated
 * @param started the start date of the membership to be updated
 * @param over the end date of the membership to be updated
 */
public record UpdateMembershipRequest(
        @NotNull @NotBlank
        String description,
        @NotNull
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        LocalDate started,
        @NotNull
        @JsonFormat(pattern = Util.DATE_FORMAT_PATTERN)
        LocalDate over) {
}
