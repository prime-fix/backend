package pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.prime.platform.vehicle.diagnosis.application.internal.outboundservices.acl.ExternalMaintenanceTrackingServiceFromVehicleDiagnosis;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.aggregates.ExpectedVisit;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.CreateExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.DeleteExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.model.commands.UpdateExpectedVisitCommand;
import pe.edu.upc.prime.platform.vehicle.diagnosis.domain.services.ExpectedVisitCommandService;
import pe.edu.upc.prime.platform.vehicle.diagnosis.infrastructure.persistence.jpa.repositories.ExpectedVisitRepository;

import java.util.Optional;

/**
 * Implementation of the ExpectedVisitCommandService interface.
 */
@Service
public class ExpectedVisitCommandServiceImpl implements ExpectedVisitCommandService {
    /**
     * The Expected Visit repository.
     */
    private final ExpectedVisitRepository expectedVisitRepository;

    /**
     * The external maintenance tracking service.
     */
    private final ExternalMaintenanceTrackingServiceFromVehicleDiagnosis externalMaintenanceTrackingServiceFromVehicleDiagnosis;

    /**
     * Constructor for ExpectedVisitCommandServiceImpl.
     *
     * @param expectedVisitRepository the Expected Visit repository
     */
    public ExpectedVisitCommandServiceImpl(ExpectedVisitRepository expectedVisitRepository,
                                           ExternalMaintenanceTrackingServiceFromVehicleDiagnosis externalMaintenanceTrackingServiceFromVehicleDiagnosis) {
        this.expectedVisitRepository = expectedVisitRepository;
        this.externalMaintenanceTrackingServiceFromVehicleDiagnosis = externalMaintenanceTrackingServiceFromVehicleDiagnosis;
    }

    /**
     * Handles the creation of a new expected visit.
     *
     * @param command the command containing the expected visit information
     * @return the ID of the created expected visit
     */
    @Override
    public Long handle(CreateExpectedVisitCommand command) {
        // Validate if Expected Visit with the same Visit ID already exists
        if (this.expectedVisitRepository.existsByVisitId(command.visitId())) {
            throw new IllegalArgumentException(
                    String.format("[ExpectedVisitCommandServiceImpl] Expected Visit with Visit ID: %s already exists",
                            command.visitId().visitId())
            );
        }

        // Validate if Vehicle ID exists in external Maintenance Tracking Service
        if (!this.externalMaintenanceTrackingServiceFromVehicleDiagnosis.existsVehicleById(command.vehicleId().value())) {
            throw new IllegalArgumentException(
                    String.format("[ExpectedVisitCommandServiceImpl] Vehicle with ID: %s does not exist in Maintenance Tracking Service",
                            command.vehicleId().value()));
        }

        // Create and save Expected Visit
        var expectedVisit = new ExpectedVisit(command);

        // Persist Expected Visit
        try {
            this.expectedVisitRepository.save(expectedVisit);
        } catch (Exception e) {
            throw new PersistenceException("[ExpectedVisitCommandServiceImpl] Error saving Expected Visit: "
                    + e.getMessage());
        }
        return expectedVisit.getId();
    }

    /**
     * Handles the update of an existing expected visit.
     *
     * @param command the command containing the updated expected visit information
     * @return an Optional containing the updated ExpectedVisit if successful
     */
    @Override
    public Optional<ExpectedVisit> handle(UpdateExpectedVisitCommand command) {
        // Validate if Expected Visit exists
        if (!this.expectedVisitRepository.existsById(command.expectedVisitId())) {
            throw new NotFoundIdException(ExpectedVisit.class, command.expectedVisitId());
        }

        // Validate if Expected Visit with the same Visit ID already exists
        if (this.expectedVisitRepository.existsByVisitIdAndIdIsNot(command.visitId(), command.expectedVisitId())) {
            throw new IllegalArgumentException(
                    String.format("[ExpectedVisitCommandServiceImpl] Visit ID: %s is already associated with another Expected Visit",
                            command.visitId().visitId())
            );
        }

        // Validate if Vehicle ID exists in external Maintenance Tracking Service
        if (!this.externalMaintenanceTrackingServiceFromVehicleDiagnosis.existsVehicleById(command.vehicleId().value())) {
            throw new IllegalArgumentException(
                    String.format("[ExpectedVisitCommandServiceImpl] Vehicle with ID: %s does not exist in Maintenance Tracking Service",
                            command.vehicleId().value()));
        }

        // Update and save Expected Visit
        var expectedVisitToUpdate = this.expectedVisitRepository.findById(command.expectedVisitId()).get();

        // Update Expected Visit
        expectedVisitToUpdate.updateExpectedVisit(command);

        // Persist Expected Visit
        try {
            this.expectedVisitRepository.save(expectedVisitToUpdate);
            return Optional.of(expectedVisitToUpdate);
        } catch (Exception e) {
            throw new PersistenceException("[ExpectedVisitCommandServiceImpl] Error updating Expected Visit: "
                    + e.getMessage());
        }
    }

    /**
     * Handles the deletion of an expected visit.
     *
     * @param command the command containing the ID of the expected visit to be deleted
     */
    @Override
    public void handle(DeleteExpectedVisitCommand command) {
        // Validate if Expected Visit exists
        if (!this.expectedVisitRepository.existsById(command.expectedVisitId())) {
            throw new NotFoundIdException(ExpectedVisit.class, command.expectedVisitId());
        }

        // Delete Expected Visit
        try {
            this.expectedVisitRepository.deleteById(command.expectedVisitId());
        } catch (Exception e) {
            throw new PersistenceException("[ExpectedVisitCommandServiceImpl] Error deleting Expected Visit: "
                    + e.getMessage());
        }
    }
}
