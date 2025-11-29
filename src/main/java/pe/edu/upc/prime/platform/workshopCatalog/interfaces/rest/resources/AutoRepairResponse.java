package pe.edu.upc.prime.platform.workshopCatalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import pe.edu.upc.prime.platform.iam.domain.model.aggregates.UserAccount;
import pe.edu.upc.prime.platform.workshopCatalog.domain.model.valueobjects.UserAccountId;

public record AutoRepairResponse(
        @JsonProperty("auto_repair_id")
        String autoRepairId,
        String contact_email,
        String technician_count,
        String RUC,
        @JsonProperty("user_account_id")
        Long userAccountId

) {
}
