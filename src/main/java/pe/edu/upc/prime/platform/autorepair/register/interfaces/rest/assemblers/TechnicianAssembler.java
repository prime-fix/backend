package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianCommand;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.CreateTechnicianRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.UpdateTechnicianRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.TechnicianResponse;
import pe.edu.upc.prime.platform.shared.domain.model.valueobjects.AutoRepairId;

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
                request.name(),
                request.lastName(),
                new AutoRepairId(request.autoRepairId())
        );
    }

    /**
     * Converts an UpdateTechnicianRequest into an UpdateTechnicianCommand.
     *
     * @param technician_id The technician ID to update.
     * @param request The update technician request.
     * @return The corresponding UpdateTechnicianCommand.
     */
    public static UpdateTechnicianCommand toCommandFromRequest(Long technician_id, UpdateTechnicianRequest request) {
        return new UpdateTechnicianCommand(
                technician_id,
                request.name(),
                request.lastName(),
                new AutoRepairId(request.autoRepairId())
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
                entity.getId().toString(),
                entity.getName(),
                entity.getLastName(),
                entity.getAutoRepairId().value()
        );
    }
}
