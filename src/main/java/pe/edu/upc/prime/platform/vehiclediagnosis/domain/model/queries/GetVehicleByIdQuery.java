package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.queries;

import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.valueobjects.VehicleId;

/**
 * Query to get a vehicle by its ID.
 *
 * @param vehicleId the ID of the vehicle to retrieve
 */
public record GetVehicleByIdQuery(VehicleId vehicleId) {
}
