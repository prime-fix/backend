package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueObjects.UserAccountId;

public record CreateAutoRepairRequest(
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
