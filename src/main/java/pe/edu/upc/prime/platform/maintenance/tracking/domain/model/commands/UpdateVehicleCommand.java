package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

import pe.edu.upc.prime.platform.shared.utils.Util;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;

import java.util.Objects;

/**
 * Command to update an existing vehicle.
 *
 * @param idVehicle the ID of the vehicle to be updated
 * @param color the color of the vehicle to be updated
 * @param model the model of the vehicle to be updated
 * @param idUser the ID of the user associated with the vehicle
 * @param vehicleInformation the vehicle information value object containing brand, plate, and type
 * @param maintenanceStatus the maintenance status of the vehicle to be updated
 */
public record UpdateVehicleCommand(String idVehicle, String color, String model, String idUser,
                                   VehicleInformation vehicleInformation, int maintenanceStatus) {

    public UpdateVehicleCommand {
        Objects.requireNonNull(idVehicle, "[UpdateVehicleCommand] idVehicle must not be null");
        Objects.requireNonNull(color, "[UpdateVehicleCommand] color must not be null");
        Objects.requireNonNull(model, "[UpdateVehicleCommand] model must not be null");
        Objects.requireNonNull(idUser, "[UpdateVehicleCommand] idUser must not be null");
        Objects.requireNonNull(vehicleInformation, "[UpdateVehicleCommand] vehicleInformation must not be null");

        if (maintenanceStatus < Util.MIN_MAINTENANCE_STATUS || maintenanceStatus > Util.MAX_MAINTENANCE_STATUS) {
            throw new IllegalArgumentException(
                    String.format("[UpdateVehicleCommand] The maintenance status must be between %s and %s",
                            Util.MIN_MAINTENANCE_STATUS, Util.MAX_MAINTENANCE_STATUS));
        }
    }
}
