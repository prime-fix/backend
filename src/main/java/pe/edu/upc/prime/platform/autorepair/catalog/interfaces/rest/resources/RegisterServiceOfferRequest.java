package pe.edu.upc.prime.platform.autorepair.catalog.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterServiceOfferRequest(
        @NotNull
        @JsonProperty("service_id")
        Long serviceId,


        @NotNull
        BigDecimal price

) {
}
