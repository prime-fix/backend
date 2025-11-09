package pe.edu.upc.prime.platform.vehiclediagnosis.application.internal.queryservices;

import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.queries.GetAllDiagnosticsByVehicleIdQuery;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.services.DiagnosisQueryService;
import pe.edu.upc.prime.platform.vehiclediagnosis.infrastructure.persistence.jpa.repositories.DiagnosisRepository;

import java.util.List;

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

    public List<Diagnostic> handle(GetAllDiagnosticsByVehicleIdQuery query) {
        return this.diagnosisRepository.findAll();
    }

}
