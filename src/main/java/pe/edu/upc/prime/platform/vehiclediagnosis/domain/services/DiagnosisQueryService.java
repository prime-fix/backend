package pe.edu.upc.prime.platform.vehiclediagnosis.domain.services;

import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.queries.GetAllDiagnosticsByVehicleIdQuery;

import java.util.List;

public interface DiagnosisQueryService {
    List<Diagnostic> handle(GetAllDiagnosticsByVehicleIdQuery query);
}
