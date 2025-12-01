package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Request record for creating a new visit.
 * @param vehicleId the ID of the vehicle
 * @param serviceId the ID of the service
 * @param failure the failure description
 * @param timeVisit  the time of the visit
 * @param autoRepairId the ID of the auto repair
 */
public record CreateVisitRequest(


        @NotNull
        @Min(0)
        @JsonProperty("vehicle_id")
        Long vehicleId,

        @NotNull
        @NotBlank
        @JsonProperty("service_id")
        Long serviceId,

        @NotNull
        @NotBlank
        String failure,

        @NotNull
        @JsonProperty("timeVisit")
        LocalDateTime timeVisit,

        @NotNull
        @Min(0)
        @JsonProperty("auto_repair_id")
        Long autoRepairId) {
}
