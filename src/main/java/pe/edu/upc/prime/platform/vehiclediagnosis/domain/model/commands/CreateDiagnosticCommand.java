package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands;

import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.valueobjects.VehicleId;

public record CreateDiagnosticCommand(VehicleId vehicleId, String diagnosis, Float price) {
}
