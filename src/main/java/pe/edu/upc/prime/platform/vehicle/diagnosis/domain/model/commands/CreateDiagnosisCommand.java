package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;

import java.util.Objects;

/**
 * Command to create a new vehicle diagnosis.
 *
 * @param vehicleId the ID of the vehicle
 * @param diagnosis the diagnosis description
 * @param price the price of the diagnosis
 */
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
