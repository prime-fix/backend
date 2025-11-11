package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.valueobjects.IdAutoRepair;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.CreateTechnicianRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.UpdateTechnicianRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.TechnicianResponse;

/**
 * Assembler for converting between Technician-related requests, commands, and responses.
 */
public class TechnicianAssembler {

    /**
     * Converts a CreateTechnicianRequest into a CreateTechnicianCommand.
     *
     * @param request The create technician request.
     * @return The corresponding CreateTechnicianCommand.
     */
    public static CreateTechnicianCommand toCommandFromRequest(CreateTechnicianRequest request) {
        return new CreateTechnicianCommand(
                request.idTechnician(),
                request.name(),
                request.lastName(),
                new IdAutoRepair(request.idAutoRepair())
        );
    }

    /**
     * Converts an UpdateTechnicianRequest into an UpdateTechnicianCommand.
     *
     * @param idTechnician The technician ID to update.
     * @param request The update technician request.
     * @return The corresponding UpdateTechnicianCommand.
     */
    public static UpdateTechnicianCommand toCommandFromRequest(String idTechnician, UpdateTechnicianRequest request) {
        return new UpdateTechnicianCommand(
                idTechnician,
                request.name(),
                request.lastName(),
                new IdAutoRepair(request.idAutoRepair())
        );
    }

    /**
     * Converts a Technician entity into a TechnicianResponse.
     *
     * @param entity The technician entity.
     * @return The corresponding TechnicianResponse.
     */
    public static TechnicianResponse toResponseFromEntity(Technician entity) {
        return new TechnicianResponse(
                entity.getIdTechnician(),
                entity.getName(),
                entity.getLastName(),
                entity.getIdAutoRepairValue()
        );
    }
}
