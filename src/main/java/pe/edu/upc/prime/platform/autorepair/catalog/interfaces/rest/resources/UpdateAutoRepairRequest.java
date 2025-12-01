package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request to update an auto repair
 *
 * @param contactEmail the contact email of the auto repair to be updated
 * @param technicianCount the number of technicians in the auto repair to be updated
 * @param ruc the RUC of the auto repair to be updated
 * @param userAccountId the user account ID associated with the auto repair to be updated
 */
public record UpdateAutoRepairRequest(
        @NotBlank
        @NotNull
        @JsonProperty("contact_email")
        String contactEmail,

        @NotBlank
        @NotNull
        @JsonProperty("technician_count")
        Integer technicianCount,

        @NotBlank
        @NotNull
        String ruc,

        @NotBlank
        @NotNull
        @JsonProperty("user_account_id")
        Long userAccountId) {
}
