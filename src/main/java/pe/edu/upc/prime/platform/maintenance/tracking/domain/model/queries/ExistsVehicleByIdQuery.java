package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries;

/**
 * Query to check if a vehicle exists by its id.
 *
 * @param vehicleId the id of the vehicle.
 */
public record ExistsVehicleByIdQuery(Long vehicleId) {
}
