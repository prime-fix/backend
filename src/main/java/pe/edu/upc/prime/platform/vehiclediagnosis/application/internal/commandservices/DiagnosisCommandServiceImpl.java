package pe.edu.upc.prime.platform.vehiclediagnosis.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands.CreateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands.DeleteDiagnosisCommand;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.model.commands.UpdateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehiclediagnosis.domain.services.DiagnosisCommandService;
import pe.edu.upc.prime.platform.vehiclediagnosis.infrastructure.persistence.jpa.repositories.DiagnosisRepository;

import java.util.Optional;

@Service
    public class DiagnosisCommandServiceImpl implements DiagnosisCommandService {
    private final DiagnosisRepository diagnosisRepository;
    public DiagnosisCommandServiceImpl(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public String handle(CreateDiagnosisCommand command) {
        var diagnostic = new Diagnostic(command);
        try {
            this.diagnosisRepository.save(diagnostic);
        } catch (Exception e) {
            throw new PersistenceException("[DiagnosisCommandServiceImpl] Error while saving diagnostic: "
                    + e.getMessage());
        }
        return diagnostic.getId().toString();
    }

    @Override
    public Optional<Diagnostic> handle(UpdateDiagnosisCommand command) {
        // Validate if the diagnosis already exists
        var diagnosticId = command.diagnosisId();
        if (!this.diagnosisRepository.existsById(Long.valueOf(diagnosticId))) {
            throw new NotFoundIdException(Diagnostic.class, diagnosticId);
        }

        // Update the diagnosis
        var diagnosisToUpdate = this.diagnosisRepository.findById(Long.valueOf(diagnosticId)).get();
        diagnosisToUpdate.updateDiagnostic(command);

        try {
            var updatedDiagnosis = this.diagnosisRepository.save(diagnosisToUpdate);
            return Optional.of(updatedDiagnosis);
        } catch (Exception e) {
            throw new PersistenceException("[DiagnosisCommandServiceImpl] Error while updating diagnosis: "
                    + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteDiagnosisCommand command) {
        // If the profile does not exist, throw an exception
        if (!this.diagnosisRepository.existsById(Long.valueOf(command.idDiagnostic()))) {
            throw new NotFoundIdException(Diagnostic.class, command.idDiagnostic());
        }

        // Try to delete the profile, if an error occurs, throw an exception
        try {
            this.diagnosisRepository.deleteById(Long.valueOf(command.idDiagnostic()));
        } catch (Exception e) {
            throw new PersistenceException("[DiagnosisCommandServiceImpl] Error while deleting diagnosis: "
                    + e.getMessage());
        }
    }
}
