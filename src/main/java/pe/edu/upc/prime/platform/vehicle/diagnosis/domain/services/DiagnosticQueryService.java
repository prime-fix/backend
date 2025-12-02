package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetDiagnosticsByVehicleIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetAllDiagnosticsQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetDiagnosticByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling diagnostic queries.
 */
public interface DiagnosticQueryService {
    /**
     * Handle the query to get all diagnostics.
     *
     * @param query the query to get all diagnostics
     * @return a list of all profiles
     */
    List<Diagnostic> handle(GetDiagnosticsByVehicleIdQuery query);

    /**
     * Handle the query to get a diagnostic by its ID.
     * @param query the query to get a diagnostic by ID
     * @return an Optional containing the diagnostic if found, or empty if not found
     */
    Optional<Diagnostic> handle(GetDiagnosticByIdQuery query);

    /**
     * Handle the query to get all diagnostics.
     *
     * @param getAllDiagnosticsQuery the query to get all diagnostics
     * @return a list of all diagnostics
     */
    List<Diagnostic> handle(GetAllDiagnosticsQuery getAllDiagnosticsQuery);
}
