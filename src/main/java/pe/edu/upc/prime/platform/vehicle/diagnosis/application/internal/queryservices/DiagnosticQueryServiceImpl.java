package pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetDiagnosticsByVehicleIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetAllDiagnosticsQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.queries.GetDiagnosticByIdQuery;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.DiagnosticQueryService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.infrastructure.persistence.jpa.repositories.DiagnosticRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the DiagnosticQueryService interface.
 */
@Service
public class DiagnosticQueryServiceImpl implements DiagnosticQueryService {
    private final DiagnosticRepository diagnosticRepository;

    /**
     * Constructor for DiagnosisQueryServiceImpl.
     *
     * @param diagnosticRepository the repository to access diagnosis data
     */
    public DiagnosticQueryServiceImpl(DiagnosticRepository diagnosticRepository) {
        this.diagnosticRepository = diagnosticRepository;
    }

    /**
     * Handles query to get all diagnostics by vehicle ID.
     *
     * @param query the query to get all diagnostics
     * @return a list of all diagnostics
     */
    @Override
    public List<Diagnostic> handle(GetDiagnosticsByVehicleIdQuery query) {
        return diagnosticRepository.findByVehicleId(query.vehicleId());
    }

    /**
     * Handles query to get a diagnostic by its ID.
     *
     * @param query the query to get a diagnostic by ID
     * @return an Optional containing the diagnostic if found, or empty if not found
     */
    @Override
    public Optional<Diagnostic> handle(GetDiagnosticByIdQuery query) {
        return Optional.ofNullable(this.diagnosticRepository.findById(query.diagnosticId())
                .orElseThrow(() -> new NotFoundIdException(Diagnostic.class, query.diagnosticId())));
    }

    /**
     * Handles query to get all diagnostics.
     *
     * @param getAllDiagnosticsQuery the query to get all diagnostics
     * @return a list of all diagnostics
     */
    @Override
    public List<Diagnostic> handle(GetAllDiagnosticsQuery getAllDiagnosticsQuery) {
        return diagnosticRepository.findAll();
    }
}
