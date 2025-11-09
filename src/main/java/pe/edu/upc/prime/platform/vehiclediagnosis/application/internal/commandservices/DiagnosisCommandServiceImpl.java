package pe.edu.upc.prime.platform.vehiclediagnosis.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands.CreateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.services.DiagnosisCommandService;
import pe.edu.upc.prime.platform.vehiclediagnosis.infrastructure.persistence.jpa.repositories.DiagnosisRepository;

public class DiagnosisCommandServiceImpl implements DiagnosisCommandService {
    private final DiagnosisRepository diagnosisRepository;
    public DiagnosisCommandServiceImpl(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public Long handle(CreateDiagnosticCommand command) {
        var diagnostic = new Diagnostic(command);
        try {
            this.diagnosisRepository.save(diagnostic);
        } catch (Exception e) {
            throw new PersistenceException("[ProfileCommandServiceImpl] Error while saving diagnostic: "
                    + e.getMessage());
        }
        return diagnostic.getVehicleId().vehicleId();
    }
}
