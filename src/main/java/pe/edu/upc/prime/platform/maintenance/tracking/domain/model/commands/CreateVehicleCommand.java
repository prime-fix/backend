package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.UserId;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.valueobjects.VehicleInformation;

import java.util.Objects;

/**
 * Command to create a new vehicle.
 *
 * @param color the color of the vehicle to be created
 * @param model the model of the vehicle to be created
 * @param userId the identifier of the user associated with the vehicle to be created
 * @param vehicleInformation the vehicle information value object containing brand, plate, and type
 */
public record CreateVehicleCommand(String color, String model, UserId userId,
                                   VehicleInformation vehicleInformation) {
    /**
     * Constructor with validation.
     */
    public CreateVehicleCommand {

        Objects.requireNonNull(color, "[CreateVehicleCommand] color must not be null");
        Objects.requireNonNull(model, "[CreateVehicleCommand] model must not be null");
        Objects.requireNonNull(userId, "[CreateVehicleCommand] user id must not be null");
        Objects.requireNonNull(vehicleInformation, "[CreateVehicleCommand] vehicle information must not be null");
    }
}
