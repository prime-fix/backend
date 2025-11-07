package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.commands;

/**
 * Command to delete a vehicle by its ID.
 *
 * @param idVehicle the ID of the vehicle to be deleted
 */
public record DeleteVehicleCommand(String idVehicle) {
}
