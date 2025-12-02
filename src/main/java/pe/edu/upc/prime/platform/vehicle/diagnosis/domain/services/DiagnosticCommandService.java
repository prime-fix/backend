package pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services;

import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.DeleteDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosticCommand;

import java.util.Optional;

/**
 * Service interface for handling diagnostic commands.
 */
public interface DiagnosticCommandService {
    /**
     * Handles the creation of a new diagnostic based on the provided command.
     *
     * @param command the command containing the diagnostic information
     * @return the ID of the newly created Diagnostic
     */
    Long handle(CreateDiagnosticCommand command);

    /**
     * Handles the update of an existing diagnostic based on the provided command.
     *
     * @param command the command containing the updated diagnostic information
     * @return an Optional containing the updated Diagnostic if the update was successful, or empty if not found
     */
    Optional<Diagnostic> handle(UpdateDiagnosticCommand command);

    /**
     * Handles the deletion of a diagnostic based on the provided command.
     *
     * @param command the command containing the ID of the diagnostic to be deleted
     */
    void handle(DeleteDiagnosticCommand command);
}
