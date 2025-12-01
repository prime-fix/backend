package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Request record for registering a service offer.
 *
 * @param serviceId the ID of the service
 * @param price the price of the service offer
 */
public record RegisterServiceOfferRequest(
        @NotNull
        @JsonProperty("service_id")
        Long serviceId,


        @NotNull
        BigDecimal price) {
}
