package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request to update a vehicle.
 *
 * @param color             The color of the vehicle.
 * @param model             The model of the vehicle.
 * @param idUser            The identifier of the user associated with the vehicle.
 * @param vehicleBrand     The brand of the vehicle.
 * @param vehiclePlate     The license plate of the vehicle.
 * @param vehicleType      The type of the vehicle.
 * @param maintenanceStatus The maintenance status of the vehicle.
 */
public record UpdateVehicleRequest(
        @NotNull @NotBlank
        @Size(min = 1, max = 50)
        @JsonProperty("color")
        String color,

        @NotNull @NotBlank
        @Size(min = 1, max = 100)
        @JsonProperty("model")
        String model,

        @NotNull @NotBlank
        @JsonProperty("id_user")
        String idUser,

        @NotNull @NotBlank
        @JsonProperty("vehicle_brand")
        String vehicleBrand,

        @NotNull @NotBlank
        @JsonProperty("vehicle_plate")
        String vehiclePlate,

        @NotNull @NotBlank
        @JsonProperty("vehicle_type")
        String vehicleType,

        @NotNull @NotBlank
        @JsonProperty("maintenance_status")
        int maintenanceStatus) {
}
