package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueobjects.UserAccountId;

public record CreateAutoRepairRequest(
        @NotBlank
        @NotNull
        String contact_email,

        @NotNull
        @Min(0)
        Integer technician_count,

        @NotBlank
        @NotNull
        @JsonProperty("ruc")
        String ruc,

        @NotNull
        @Min(0)
        @JsonProperty("user_account_id")
        Long userAccountId

) {
}
