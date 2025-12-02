package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries;

/**
 * Query to get vehicles by maintenance status.
 *
 * @param maintenanceStatus the maintenance status to filter vehicles
 */
public record GetVehiclesByMaintenanceStatusQuery(Integer maintenanceStatus) {
}
