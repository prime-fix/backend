package pe.edu.upc.prime.platform.autorepair.register.domain.services;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.DeleteTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianCommand;

import java.util.Optional;

/**
 * Service interface for handling Technician-related commands.
 */
public interface TechnicianCommandService {

    /**
     * Handles the creation of a new Technician.
     *
     * @param command The command containing technician details.
     * @return The created Technician entity.
     */
    Technician handle(CreateTechnicianCommand command);

    /**
     * Handles updating an existing Technician.
     *
     * @param command The command containing updated technician data.
     * @return The updated Technician entity.
     */
    Technician handle(UpdateTechnicianCommand command);

    /**
     * Handles deleting an existing Technician by ID.
     *
     * @param idTechnician The ID of the Technician to delete.
     */
    void handle(DeleteTechnicianCommand idTechnician);
}
