package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for Expected Visit.
 *
 * @param id the expected visit id
 * @param stateVisit the state of the visit
 * @param visitId the identifier of the visit
 * @param isScheduled the scheduled status of the visit
 */
public record ExpectedVisitResponse(
        Long id,
        @JsonProperty("state_visit") String stateVisit,
        @JsonProperty("visit_id") Long visitId,
        @JsonProperty("is_scheduled") Boolean isScheduled,
        @JsonProperty("vehicle_id") Long vehicleId) {
}
