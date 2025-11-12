package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetAllDiagnosticsByVehicleIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetAllDiagnosticsQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetDiagnosticByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling diagnosis-related queries.
 */
public interface DiagnosisQueryService {
    /**
     * Handle the query to get all diagnostics.
     *
     * @param query the query to get all diagnostics
     * @return a list of all profiles
     */
    List<Diagnostic> handle(GetAllDiagnosticsByVehicleIdQuery query);

    Optional<Diagnostic> handle(GetDiagnosticByIdQuery query);

    List<Diagnostic> handle(GetAllDiagnosticsQuery getAllDiagnosticsQuery);
}
