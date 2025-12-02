package pe.edu.upc.prime.platform.vehicle.diagnosis.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Request to update a vehicle diagnostic.
 *
 * @param diagnosis the updated diagnosis description
 * @param price the updated price of the diagnosis
 */
public record UpdateDiagnosticRequest(
        @JsonProperty("price")
        @NotNull
        @Positive(message = "Price must be a positive value")
        Float price,

        @JsonProperty("vehicle_id")
        @NotNull
        Long vehicleId,

        @JsonProperty("diagnosis")
        @NotNull @NotBlank
        @Size(min = 5, max = 255, message = "Diagnosis description must be between 5 and 255 characters")
        String diagnosis) {

}