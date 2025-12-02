package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request to create an Auto Repair Shop
 *
 * @param contactEmail the contact email of the auto repair shop
 * @param ruc the RUC of the auto repair shop
 * @param userAccountId the user account ID associated with the auto repair shop
 */
public record CreateAutoRepairRequest(
        @NotBlank
        @NotNull
        @JsonProperty("contact_email")
        String contactEmail,

        @NotBlank
        @NotNull
        String ruc,

        @NotNull
        @Min(0)
        @JsonProperty("user_account_id")
        Long userAccountId) {
}
