package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands;

/**
 * Command to delete a Diagnostic
 *
 * @param diagnosticId the ID of the Diagnostic to be deleted
 */
public record DeleteDiagnosticCommand(Long diagnosticId) {
}
