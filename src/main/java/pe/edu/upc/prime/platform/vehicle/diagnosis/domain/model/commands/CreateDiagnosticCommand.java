package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;

import java.util.Objects;

/**
 * Command to create a new Diagnostic.
 *
 * @param price the price of the diagnostic
 * @param vehicleId the identifier of the vehicle
 * @param diagnosis the diagnosis details
 */
public record CreateDiagnosticCommand(Float price, VehicleId vehicleId, String diagnosis) {

    public CreateDiagnosticCommand {
        Objects.requireNonNull(price, "[CreateDiagnosticCommand] price must not be null");
        Objects.requireNonNull(vehicleId, "[CreateDiagnosticCommand] vehicleId must not be null");
        Objects.requireNonNull(diagnosis, "[CreateDiagnosticCommand] diagnosis must not be null");

        if (price < 0) {
            throw new IllegalArgumentException("[CreateDiagnosticCommand] price must be non-negative");
        }
        if (diagnosis.isBlank()) {
            throw new IllegalArgumentException("[CreateDiagnosticCommand] diagnosis cannot be blank");
        }
    }
}
