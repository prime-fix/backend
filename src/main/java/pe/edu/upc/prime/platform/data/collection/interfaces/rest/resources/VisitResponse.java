package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

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
