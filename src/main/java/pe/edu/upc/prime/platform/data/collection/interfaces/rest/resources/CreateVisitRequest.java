package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

/**
 * Request record for creating a new visit.
 * @param visitId  the ID of the visit
 * @param vehicleId the ID of the vehicle
 * @param serviceId the ID of the service
 * @param failure the failure description
 * @param timeVisit  the time of the visit
 * @param autoRepairId the ID of the auto repair
 */
public record CreateVisitRequest(

        @NotBlank
        @NotNull
        @JsonProperty("visit_id")
        String visitId,

        @NotNull
        @NotBlank
        @JsonProperty("vehicle_id")
        String vehicleId,

        @NotNull
        @NotBlank
        @JsonProperty("service_id")
        String serviceId,

        @NotNull
        @NotBlank
        String failure,

        @NotNull
        @NotBlank
        Date timeVisit,

        @NotNull
        @NotBlank
        @JsonProperty("auto_repair_id")
        String autoRepairId) {
}
