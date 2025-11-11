package pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands;

import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.valueobjects.VehicleId;

import java.util.Objects;

public record CreateDiagnosisCommand(
        VehicleId vehicleId,
        String diagnosis,
        Float price
) {
    public CreateDiagnosisCommand {
        Objects.requireNonNull(vehicleId, "Vehicle ID must not be null");
        Objects.requireNonNull(diagnosis, "Diagnosis description must not be null");
        Objects.requireNonNull(price, "Diagnosis price must not be null");
    }
}
