package pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.outboundservices.acl.ExternalMaintenanceTrackingServiceFromVehicleDiagnosis;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.Diagnostic;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.DeleteDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateDiagnosticCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.DiagnosticCommandService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.infrastructure.persistence.jpa.repositories.DiagnosticRepository;
import pe.edu.upc.prime.platform.vehicle.diagnosis.infrastructure.persistence.jpa.repositories.ExpectedVisitRepository;

import java.util.Optional;

/**
 * Implementation of the DiagnosticCommandService interface.
 */
@Service
    public class DiagnosticCommandServiceImpl implements DiagnosticCommandService {
    /**
     * The Diagnostic repository.
     */
    private final DiagnosticRepository diagnosticRepository;

    /**
     * The external maintenance tracking service.
     */
    private final ExternalMaintenanceTrackingServiceFromVehicleDiagnosis externalMaintenanceTrackingServiceFromVehicleDiagnosis;

    /**
     * Constructor for DiagnosticCommandServiceImpl.
     *
     * @param diagnosticRepository                                   the Diagnostic repository
     * @param externalMaintenanceTrackingServiceFromVehicleDiagnosis the external maintenance tracking service
     */
    public DiagnosticCommandServiceImpl(DiagnosticRepository diagnosticRepository,
                                        ExternalMaintenanceTrackingServiceFromVehicleDiagnosis externalMaintenanceTrackingServiceFromVehicleDiagnosis) {
        this.diagnosticRepository = diagnosticRepository;
        this.externalMaintenanceTrackingServiceFromVehicleDiagnosis = externalMaintenanceTrackingServiceFromVehicleDiagnosis;
    }

    /**
     * Handles the creation of a new diagnostic.
     *
     * @param command the command containing the diagnostic information
     * @return the ID of the created diagnostic
     */
    @Override
    public Long handle(CreateDiagnosticCommand command) {
        // Validate if Vehicle ID exists in external Maintenance Tracking Service
        if (!this.externalMaintenanceTrackingServiceFromVehicleDiagnosis.existsVehicleById(command.vehicleId().value())) {
            throw new IllegalArgumentException(
                    String.format("[DiagnosticCommandServiceImpl] Vehicle with ID: %s does not exist in Maintenance Tracking Service",
                            command.vehicleId().value()));
        }

        var diagnostic = new Diagnostic(command);
        try {
            this.diagnosticRepository.save(diagnostic);
        } catch (Exception e) {
            throw new PersistenceException("[DiagnosticCommandServiceImpl] Error while saving diagnostic: "
                    + e.getMessage());
        }
        return diagnostic.getId();
    }

    /**
     * Handles the update of an existing diagnostic.
     *
     * @param command the command containing the updated diagnostic information
     * @return an Optional containing the updated diagnostic, or empty if not found
     */
    @Override
    public Optional<Diagnostic> handle(UpdateDiagnosticCommand command) {
        // Validate if Diagnostic ID exists
        if (!this.diagnosticRepository.existsById(command.diagnosticId())) {
            throw new NotFoundIdException(Diagnostic.class, command.diagnosticId());
        }

        // Validate if Vehicle ID exists in external Maintenance Tracking Service
        if (!this.externalMaintenanceTrackingServiceFromVehicleDiagnosis.existsVehicleById(command.vehicleId().value())) {
            throw new IllegalArgumentException(
                    String.format("[DiagnosticCommandServiceImpl] Vehicle with ID: %s does not exist in Maintenance Tracking Service",
                            command.vehicleId().value()));
        }

        // Fetch and update the Diagnostic
        var diagnosticToUpdate = this.diagnosticRepository.findById(command.diagnosticId()).get();
        diagnosticToUpdate.updateDiagnostic(command);

        // Save the updated Diagnostic
        try {
            this.diagnosticRepository.save(diagnosticToUpdate);
            return Optional.of(diagnosticToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("[DiagnosticCommandServiceImpl] Error while updating diagnostic: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of an existing diagnostic.
     *
     * @param command the command containing the ID of the diagnostic to be deleted
     */
    @Override
    public void handle(DeleteDiagnosticCommand command) {
        // Validate if Diagnostic ID exists
        if (!this.diagnosticRepository.existsById(command.diagnosticId())) {
            throw new NotFoundIdException(Diagnostic.class, command.diagnosticId());
        }
        // Delete the Diagnostic
        try {
            this.diagnosticRepository.deleteById(command.diagnosticId());
        } catch (Exception e) {
            throw new PersistenceException("[DiagnosticCommandServiceImpl] Error while deleting diagnostic: "
                    + e.getMessage());
        }
    }
}
