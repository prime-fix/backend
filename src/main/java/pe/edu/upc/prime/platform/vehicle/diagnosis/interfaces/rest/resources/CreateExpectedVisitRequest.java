package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * Request to create an expected visit.
 *
 * @param visitId the identifier of the visit
 */
public record CreateExpectedVisitRequest(
        @NotNull
        @JsonProperty("visit_id")
        Long visitId) {
}
