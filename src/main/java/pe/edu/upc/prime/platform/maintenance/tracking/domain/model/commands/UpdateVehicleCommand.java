package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.MaintenanceStatus;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.UserId;
import pe.edu.upc.prime.platform.shared.utils.Util;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;

import java.util.Objects;

/**
 * Command to update an existing vehicle.
 *
 * @param vehicleId the ID of the vehicle to be updated
 * @param color the color of the vehicle to be updated
 * @param model the model of the vehicle to be updated
 * @param userId the ID of the user associated with the vehicle
 * @param vehicleInformation the vehicle information value object containing brand, plate, and type
 * @param maintenanceStatus the maintenance status of the vehicle to be updated
 */
public record UpdateVehicleCommand(Long vehicleId, String color, String model, UserId userId,
                                   VehicleInformation vehicleInformation, MaintenanceStatus maintenanceStatus) {

    public UpdateVehicleCommand {
        Objects.requireNonNull(vehicleId, "[UpdateVehicleCommand] idVehicle must not be null");
        Objects.requireNonNull(color, "[UpdateVehicleCommand] color must not be null");
        Objects.requireNonNull(model, "[UpdateVehicleCommand] model must not be null");
        Objects.requireNonNull(userId, "[UpdateVehicleCommand] user id must not be null");
        Objects.requireNonNull(vehicleInformation, "[UpdateVehicleCommand] vehicleInformation must not be null");

        if (maintenanceStatus != MaintenanceStatus.NOT_BEING_SERVICED && maintenanceStatus != MaintenanceStatus.WAITING
                && maintenanceStatus != MaintenanceStatus.IN_DIAGNOSIS && maintenanceStatus != MaintenanceStatus.IN_REPAIR
                && maintenanceStatus != MaintenanceStatus.TESTING && maintenanceStatus != MaintenanceStatus.READY_FOR_PICKUP
                && maintenanceStatus != MaintenanceStatus.COLLECTED) {
            throw new IllegalArgumentException(
                    "[CreateVehicleCommand] Invalid maintenance status: [" + maintenanceStatus + "]");
        }
    }
}
