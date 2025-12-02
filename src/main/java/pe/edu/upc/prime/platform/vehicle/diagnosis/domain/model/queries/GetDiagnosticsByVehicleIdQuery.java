package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;

/**
 * Query to get diagnostics by vehicle ID.
 *
 * @param vehicleId the ID of the vehicle whose diagnostics are to be retrieved
 */
public record GetDiagnosticsByVehicleIdQuery(VehicleId vehicleId) {
}
