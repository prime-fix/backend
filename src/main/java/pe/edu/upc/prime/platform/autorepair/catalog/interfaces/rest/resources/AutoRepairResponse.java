package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AutoRepairResponse(
        @JsonProperty("auto_repair_id")
        String autoRepairId,
        String contact_email,
        String technician_count,
        String RUC,
        @JsonProperty("user_account_id")
        Long userAccountId,
        @JsonProperty("service_catalog")
        List<ServiceOfferResource> serviceOffer
) {
}
