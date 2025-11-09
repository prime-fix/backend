package pe.edu.upc.prime.platform.vehiclediagnosis.domain.services;

import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands.CreateDiagnosticCommand;

public interface DiagnosisCommandService {
    Long handle(CreateDiagnosticCommand command);
}
