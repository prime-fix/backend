package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosisCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.DeleteDiagnosisCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosisCommand;

import java.util.Optional;

/**
 * Service interface for handling diagnosis-related commands.
 */
public interface DiagnosisCommandService {
    /**
     * Handles the creation of a new diagnosis based on the provided command.
     *
     * @param command the command containing the diagnosis information
     * @return the ID of the newly created diagnosis
     */
    String handle(CreateDiagnosisCommand command);
    /**
     * Handles the update of a diagnosis based on the provided command.
     *
     * @param command the command containing the updated diagnosis information
     * @return an Optional containing the updated Diagnosis if successful, or empty if not found
     */
    Optional<Diagnostic> handle(UpdateDiagnosisCommand command);
    /**
     * Handles the deletion of a diagnosis based on the provided command.
     *
     * @param command the command containing the ID of the diagnosis to be deleted
     */
    void handle(DeleteDiagnosisCommand command);
}
