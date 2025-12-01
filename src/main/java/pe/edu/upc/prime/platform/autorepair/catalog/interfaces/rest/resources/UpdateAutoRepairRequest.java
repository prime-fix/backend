package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request to update an auto repair
 *
 * @param contact_email the contact email of the auto repair to be updated
 * @param technician_count the number of technicians in the auto repair to be updated
 * @param ruc the RUC of the auto repair to be updated
 * @param userAccountId the user account ID associated with the auto repair to be updated
 */
public record UpdateAutoRepairRequest(
        @NotBlank
        @NotNull
        String contact_email,

        @NotBlank
        @NotNull
        Integer technician_count,

        @NotBlank
        @NotNull
        String ruc,

        @NotBlank
        @NotNull
        Long userAccountId) {
}
