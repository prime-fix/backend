package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

/**
 * Request to create a vehicle.
 *
 * @param color             The color of the vehicle.
 * @param model             The model of the vehicle.
 * @param userId            The identifier of the user associated with the vehicle.
 * @param vehicleBrand     The brand of the vehicle.
 * @param vehiclePlate     The license plate of the vehicle.
 * @param vehicleType      The type of the vehicle.
 */
public record CreateVehicleRequest(
        @NotNull @NotBlank
        @Size(min = 1, max = 50)
        String color,

        @NotNull @NotBlank
        @Size(min = 1, max = 100)
        String model,

        @NotNull
        @JsonProperty("user_id")
        Long userId,

        @NotNull @NotBlank
        @JsonProperty("vehicle_brand")
        String vehicleBrand,

        @NotNull @NotBlank
        @JsonProperty("vehicle_plate")
        String vehiclePlate,

        @NotNull @NotBlank
        @JsonProperty("vehicle_type")
        String vehicleType) {
}
