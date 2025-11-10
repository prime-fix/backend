package pe.edu.upc.prime.platform.maintenance.tracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Resource representation of a vehicle.
 *
 * @param idVehicle         The unique identifier of the vehicle.
 * @param color             The color of the vehicle.
 * @param model             The model of the vehicle.
 * @param idUser            The identifier of the user associated with the vehicle.
 * @param vehicleBrand     The brand of the vehicle.
 * @param vehiclePlate     The license plate of the vehicle.
 * @param vehicleType      The type of the vehicle.
 * @param maintenanceStatus The maintenance status of the vehicle.
 */
public record VehicleResponse(
        @JsonProperty("id_vehicle") String idVehicle,
        String color,
        String model,
        @JsonProperty("id_user") String idUser,
        @JsonProperty("vehicle_brand") String vehicleBrand,
        @JsonProperty("vehicle_plate") String vehiclePlate,
        @JsonProperty("vehicle_type") String vehicleType,
        @JsonProperty("maintenance_status") int maintenanceStatus) {
}
