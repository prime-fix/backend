package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.valueobjects.VehicleId;

public record GetAllDiagnosticsByVehicleIdQuery(VehicleId vehicleId) {
}
