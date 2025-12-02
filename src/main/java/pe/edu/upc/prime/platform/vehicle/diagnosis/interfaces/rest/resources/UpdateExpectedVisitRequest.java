package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request to update an expected visit.
 *
 * @param stateVisit the state of the visit
 * @param visitId the identifier of the visit
 * @param isScheduled indicates if the visit is scheduled
 */
public record UpdateExpectedVisitRequest(
        @JsonProperty("state_visit")
        @NotNull @NotBlank
        String stateVisit,

        @JsonProperty("visit_id")
        @NotNull
        Long visitId,

        @JsonProperty("is_scheduled")
        @NotNull
        Boolean isScheduled) {
}
