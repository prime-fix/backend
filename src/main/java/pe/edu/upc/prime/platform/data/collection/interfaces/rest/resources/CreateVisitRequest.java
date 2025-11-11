package pe.edu.upc.prime.platform.data.collection.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

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
