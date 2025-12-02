package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Diagnostic Response Resource.
 *
 * @param id the diagnostic id
 * @param diagnosis the diagnosis description
 * @param price the price of the diagnosis
 * @param vehicleId the associated vehicle id
 */
public record DiagnosticResponse(
        Long id,
        String diagnosis,
        Float price,
        @JsonProperty("vehicle_id") Long vehicleId
) {}