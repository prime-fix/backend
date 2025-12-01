package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Resource representation of a vehicle.
 *
 * @param id         The unique identifier of the vehicle.
 * @param color             The color of the vehicle.
 * @param model             The model of the vehicle.
 * @param userId            The identifier of the user associated with the vehicle.
 * @param vehicleBrand     The brand of the vehicle.
 * @param vehiclePlate     The license plate of the vehicle.
 * @param vehicleType      The type of the vehicle.
 * @param maintenanceStatus The maintenance status of the vehicle.
 */
public record VehicleResponse(
        Long id,
        String color,
        String model,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("vehicle_brand") String vehicleBrand,
        @JsonProperty("vehicle_plate") String vehiclePlate,
        @JsonProperty("vehicle_type") String vehicleType,
        @JsonProperty("maintenance_status") Integer maintenanceStatus) {
}
