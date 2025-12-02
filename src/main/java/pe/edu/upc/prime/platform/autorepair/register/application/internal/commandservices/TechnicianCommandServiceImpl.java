package pe.edu.upc.prime.platform.autorepair.register.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.register.application.internal.outboundservices.acl.ExternalAutoRepairCatalogServiceFromAutoRepairRegister;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.DeleteTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianCommandService;
import pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories.TechnicianRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundArgumentException;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of the TechnicianCommandService interface.
 */
@Service
public class TechnicianCommandServiceImpl implements TechnicianCommandService {

    /**
     * Repository for Technician entities.
     */
    private final TechnicianRepository technicianRepository;

    /**
     * External service to interact with Auto Repair Catalog.
     */
    private final ExternalAutoRepairCatalogServiceFromAutoRepairRegister externalAutoRepairCatalogServiceFromAutoRepairRegister;

    /**
     * Constructor for TechnicianCommandServiceImpl.
     * @param technicianRepository the Technician repository
     * @param externalAutoRepairCatalogServiceFromAutoRepairRegister the external Auto Repair Catalog service
     */
    public TechnicianCommandServiceImpl(TechnicianRepository technicianRepository,
                                        ExternalAutoRepairCatalogServiceFromAutoRepairRegister externalAutoRepairCatalogServiceFromAutoRepairRegister) {
        this.technicianRepository = technicianRepository;
        this.externalAutoRepairCatalogServiceFromAutoRepairRegister = externalAutoRepairCatalogServiceFromAutoRepairRegister;
    }

    /**
     * Creates a new Technician.
     */
    @Override
    public Long handle(CreateTechnicianCommand command) {
        // Validate existence of the associated Auto Repair
        if (!this.externalAutoRepairCatalogServiceFromAutoRepairRegister
                .existsAutoRepairById(command.autoRepairId().value())) {
            throw new NotFoundArgumentException(
                    String.format("[TechnicianCommandServiceImpl] Auto Repair ID: %d does not exist in the External Auto Repair Catalog Service.",
                            command.autoRepairId().value())
            );
        }

        // Create a new Technician instance from the command
        var technician = new Technician(command);

        // Increment the technicians count in the associated Auto Repair
        try {
            this.technicianRepository.save(technician);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[TechnicianCommandServiceImpl] Error while saving technician: " + e.getMessage());
        }
        return technician.getId();
    }

    /**
     * Updates an existing Technician.
     */
    @Override
    public Optional<Technician> handle(UpdateTechnicianCommand command) {
        var technicianId = command.technicianId();

        // Validate existence of the technician
        if (!this.technicianRepository.existsById(technicianId)) {
            throw new NotFoundIdException(Technician.class, technicianId);
        }

        // Update the technician with new data from the command
        var technicianToUpdate = this.technicianRepository.findById(technicianId).get();

        // Apply updates
        technicianToUpdate.updateTechnician(command);

        // Save the updated technician
        try {
            this.technicianRepository.save(technicianToUpdate);
            return Optional.of(technicianToUpdate);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[TechnicianCommandServiceImpl] Error while updating technician: " + e.getMessage());
        }
    }

    /**
     * Deletes a Technician by ID.
     */
    @Override
    public void handle(DeleteTechnicianCommand command) {
        // Retrieve the technician to be deleted
        var technician = technicianRepository.findById(command.technicianId())
                .orElseThrow(() -> new NotFoundIdException(Technician.class, command.technicianId()));

        // Delete the technician and register deletion event
        try {
            // Register deletion event
            technician.registerDeletedEvent();
            // Delete technician from repository
            technicianRepository.deleteById(command.technicianId());
        } catch (Exception e) {
            throw new PersistenceException(
                    "[TechnicianCommandServiceImpl] Error while deleting technician: "
                            + e.getMessage());
        }
    }
}
