package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * VisitResponse record to represent visit data in API responses.
 * @param visitId the unique identifier of the visit
 * @param failure the failure description
 * @param vehicleId the unique identifier of the vehicle
 * @param timeVisit the time of the visit
 * @param autoRepairId the unique identifier of the auto repair
 * @param serviceId the unique identifier of the service
 */
public record VisitResponse(
        @JsonProperty("visit_id")
        String visitId, String failure ,
        @JsonProperty("vehicle_id")
        String vehicleId,
        Date timeVisit,
        @JsonProperty("auto_repair_id")
        String autoRepairId,
        @JsonProperty("service_id")
        String serviceId
) {
}
