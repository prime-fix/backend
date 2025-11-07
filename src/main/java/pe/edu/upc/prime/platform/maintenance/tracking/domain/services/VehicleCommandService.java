package pe.edu.upc.prime.platform.maintenance.tracking.domain.services;

import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.aggregates.Vehicle;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.CreateVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.DeleteVehicleCommand;
import pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands.UpdateVehicleCommand;

import java.util.Optional;

/**
 * Service interface for handling vehicle-related commands.
 */
public interface VehicleCommandService {
    /**
     * Handles the creation of a new vehicle based on the provided command.
     *
     * @param command the command containing the vehicle information
     * @return the ID of the newly created vehicle
     */
    String handle(CreateVehicleCommand command);

    /**
     * Handles the update of a vehicle based on the provided command.
     *
     * @param command the command containing the updated vehicle information
     * @return the updated Vehicle wrapped in an Optional, or empty if not found
     */
    Optional<Vehicle> handle(UpdateVehicleCommand command);

    /**
     * Handles the deletion of a vehicle based on the provided command.
     *
     * @param command the command containing the ID of the vehicle to be deleted
     */
    void handle(DeleteVehicleCommand command);
}
