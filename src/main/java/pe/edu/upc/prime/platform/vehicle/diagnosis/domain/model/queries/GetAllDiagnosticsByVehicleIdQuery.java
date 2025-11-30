package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;

public record GetAllDiagnosticsByVehicleIdQuery(VehicleId vehicleId) {
}
