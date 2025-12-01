package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.autorepair.catalog.domain.model.valueobjects.UserAccountId;

public record UpdateAutoRepairRequest(
        @NotBlank
        @NotNull
        String contact_email,

        @NotBlank
        @NotNull
        Integer technician_count,

        @NotBlank
        @NotNull
        String RUC,

        @NotBlank
        @NotNull
        UserAccountId userAccountId
) {
}
