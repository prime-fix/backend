package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * VisitResponse record to represent visit data in API responses.
 * @param id the unique identifier of the visit
 * @param failure the failure description
 * @param vehicleId the unique identifier of the vehicle
 * @param timeVisit the time of the visit
 * @param autoRepairId the unique identifier of the auto repair
 * @param serviceId the unique identifier of the service
 */
public record VisitResponse(
        Long id,
        String failure ,
        @JsonProperty("vehicle_id") Long vehicleId,
        @JsonProperty("time_visit") LocalDateTime timeVisit,
        @JsonProperty("auto_repair_id") Long autoRepairId,
        @JsonProperty("service_id") Long serviceId
) {
}
