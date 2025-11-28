package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.queries;

import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.valueobjects.VehicleId;

public record GetAllDiagnosticsByVehicleIdQuery(VehicleId vehicleId) {
}
