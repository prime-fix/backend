package pe.edu.upc.prime.platform.autorepair.register.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.DeleteTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.services.TechnicianCommandService;
import pe.edu.upc.prime.platform.autorepair.register.infrastructure.persistence.jpa.repositories.TechnicianRepository;
import pe.edu.upc.prime.platform.shared.domain.exceptions.NotFoundIdException;

/**
 * Implementation of the TechnicianCommandService interface.
 */
@Service
public class TechnicianCommandServiceImpl implements TechnicianCommandService {

    private final TechnicianRepository technicianRepository;

    public TechnicianCommandServiceImpl(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    /**
     * Creates a new Technician.
     */
    @Override
    public Technician handle(CreateTechnicianCommand command) {
        var technician = new Technician(command);
        try {
            return technicianRepository.save(technician);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[CreateTechnicianCommand] Error while saving technician: " + e.getMessage());
        }
    }

    /**
     * Updates an existing Technician.
     */
    @Override
    public Technician handle(UpdateTechnicianCommand command) {
        var technicianOpt = technicianRepository.findById(command.idTechnician());
        if (technicianOpt.isEmpty()) {
            throw new NotFoundIdException(Technician.class, command.idTechnician());
        }

        var technicianToUpdate = technicianOpt.get();
        technicianToUpdate.updateTechnician(command);

        try {
            return technicianRepository.save(technicianToUpdate);
        } catch (Exception e) {
            throw new PersistenceException(
                    "[UpdateTechnicianCommand] Error while updating technician: " + e.getMessage());
        }
    }

    /**
     * Deletes a Technician by ID.
     */
    @Override
    public void handle(DeleteTechnicianCommand command) {
        if (!technicianRepository.existsById(command.technicianId())) {
            throw new NotFoundIdException(Technician.class, command.technicianId());
        }

        try {
            technicianRepository.deleteById(command.technicianId());
        } catch (Exception e) {
            throw new PersistenceException(
                    "[DeleteTechnicianCommand] Error while deleting technician: " + e.getMessage());
        }
    }
}
