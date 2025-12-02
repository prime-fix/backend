package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries;

/**
 * Query to get a diagnostic by its ID.
 *
 * @param diagnosticId the ID of the diagnostic to retrieve
 */
public record GetDiagnosticByIdQuery(Long diagnosticId) {
}
