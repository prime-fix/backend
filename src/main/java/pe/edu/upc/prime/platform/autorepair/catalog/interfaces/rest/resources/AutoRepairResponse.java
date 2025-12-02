package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response for Auto Repair
 *
 * @param id the ID of the auto repair
 * @param contactEmail the contact email of the auto repair
 * @param techniciansCount the number of technicians of the auto repair
 * @param ruc the RUC of the auto repair
 * @param userAccountId the user account ID associated with the auto repair
 * @param serviceOffer the list of service offers provided by the auto repair
 */
public record AutoRepairResponse(
        Long id,
        @JsonProperty("contact_email") String contactEmail,
        @JsonProperty("technicians_count") Integer techniciansCount,
        String ruc,
        @JsonProperty("user_account_id") Long userAccountId,
        @JsonProperty("service_catalog") List<ServiceOfferResource> serviceOffer) {
}
