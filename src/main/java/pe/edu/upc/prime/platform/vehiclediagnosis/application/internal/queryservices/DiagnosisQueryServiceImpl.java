package pe.edu.upc.prime.platform.vehiclediagnosis.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.queries.GetAllDiagnosticsByVehicleIdQuery;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.queries.GetAllDiagnosticsQuery;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.queries.GetDiagnosticByIdQuery;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.services.DiagnosisQueryService;
import pe.edu.upc.prime.platform.vehiclediagnosis.infrastructure.persistence.jpa.repositories.DiagnosisRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisQueryServiceImpl implements DiagnosisQueryService {
    private final DiagnosisRepository diagnosisRepository;

    /**
     * Constructor for DiagnosisQueryServiceImpl.
     *
     * @param diagnosisRepository the repository to access diagnosis data
     */
    public DiagnosisQueryServiceImpl(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    /**
     * Handles query to get all diagnostics by vehicle ID.
     */
    @Override
    public List<Diagnostic> handle(GetAllDiagnosticsByVehicleIdQuery query) {
        //return this.diagnosisRepository.findAll();
        return diagnosisRepository.findByVehicleId_VehicleId(query.vehicleId().vehicleId());
    }

    /**
     * Handles query to get a specific diagnostic by ID.
     */
    @Override
    public Optional<Diagnostic> handle(GetDiagnosticByIdQuery query) {
        return this.diagnosisRepository.findById(query.idDiagnostic());
    }

    @Override
    public List<Diagnostic> handle(GetAllDiagnosticsQuery getAllDiagnosticsQuery) {
        return diagnosisRepository.findAll();
    }

}
