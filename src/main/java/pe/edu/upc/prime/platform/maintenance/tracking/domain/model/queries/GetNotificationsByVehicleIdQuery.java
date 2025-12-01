package pe.edu.upc.prime.platform.maintenance.tracking.domain.model.queries;

/**
 * Query to get notifications by vehicle ID.
 *
 * @param vehicleId the ID of the vehicle
 */
public record GetNotificationsByVehicleIdQuery(Long vehicleId) {
}
