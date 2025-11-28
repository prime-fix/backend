package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

import pe.edu.upc.prime.platform.shared.utils.Util;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;

import java.util.Objects;

/**
 * Command to create a new vehicle.
 *
 * @param color the color of the vehicle to be created
 * @param model the model of the vehicle to be created
 * @param idUser the identifier of the user associated with the vehicle to be created
 * @param vehicleInformation the vehicle information value object containing brand, plate, and type
 * @param maintenanceStatus the maintenance status of the vehicle to be created
 */
public record CreateVehicleCommand( String color, String model, String idUser,
                                   VehicleInformation vehicleInformation, int maintenanceStatus) {
    public CreateVehicleCommand {

        Objects.requireNonNull(color, "[CreateVehicleCommand] color must not be null");
        Objects.requireNonNull(model, "[CreateVehicleCommand] model must not be null");
        Objects.requireNonNull(idUser, "[CreateVehicleCommand] id user must not be null");
        Objects.requireNonNull(vehicleInformation, "[CreateVehicleCommand] vehicle information must not be null");

        if (maintenanceStatus < Util.MIN_MAINTENANCE_STATUS || maintenanceStatus > Util.MAX_MAINTENANCE_STATUS) {
            throw new IllegalArgumentException(
                    String.format("[CreateVehicleCommand] The maintenance status must be between %s and %s",
                            Util.MIN_MAINTENANCE_STATUS, Util.MAX_MAINTENANCE_STATUS));
        }
    }
}
