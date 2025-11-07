package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries;

/**
 * Query to get a vehicle by its ID.
 *
 * @param idVehicle the ID of the vehicle to retrieve
 */
public record GetVehicleByIdQuery(String idVehicle) {
}
