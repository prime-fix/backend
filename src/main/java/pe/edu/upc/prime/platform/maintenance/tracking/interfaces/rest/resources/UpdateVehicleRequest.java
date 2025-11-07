package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import pe.edu.upc.prime.platform.shared.utils.Util;

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
        String color,

        @NotNull @NotBlank
        @Size(min = 1, max = 100)
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
        @Min(Util.MIN_MAINTENANCE_STATUS) @Max(Util.MAX_MAINTENANCE_STATUS)
        @JsonProperty("maintenance_status")
        int maintenanceStatus) {
}
