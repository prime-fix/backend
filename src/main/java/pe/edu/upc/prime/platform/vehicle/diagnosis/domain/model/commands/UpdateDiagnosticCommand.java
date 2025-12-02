package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.VehicleId;

import java.util.Objects;

/**
 * Command to update an existing Diagnostic.
 *
 * @param diagnosticId the identifier of the diagnostic to be updated
 * @param price the new price of the diagnostic to be updated
 * @param vehicleId  the identifier of the vehicle associated with the diagnostic
 * @param diagnosis the new diagnosis details
 */
public record UpdateDiagnosticCommand(Long diagnosticId, Float price, VehicleId vehicleId, String diagnosis) {
    public UpdateDiagnosticCommand {
        Objects.requireNonNull(diagnosticId, "[UpdateDiagnosticCommand] diagnostic id must not be null");
        Objects.requireNonNull(diagnosis, "[UpdateDiagnosticCommand] diagnosis must not be null");
        Objects.requireNonNull(price, "[UpdateDiagnosticCommand] price must not be null");
        Objects.requireNonNull(vehicleId, "[UpdateDiagnosticCommand] vehicleId must not be null");

        if (price < 0) {
            throw new IllegalArgumentException("[UpdateDiagnosticCommand] price must be non-negative");
        }
        if (diagnosis.isBlank()) {
            throw new IllegalArgumentException("[UpdateDiagnosticCommand] diagnosis cannot be blank");
        }
    }
}
