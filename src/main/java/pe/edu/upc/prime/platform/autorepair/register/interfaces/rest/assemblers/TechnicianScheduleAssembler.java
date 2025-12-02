package pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.assemblers;

import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.Technician;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.aggregates.TechnicianSchedule;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.CreateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.domain.model.commands.UpdateTechnicianScheduleCommand;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.CreateTechnicianScheduleRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.UpdateTechnicianScheduleRequest;
import pe.edu.upc.prime.platform.autorepair.register.interfaces.rest.resources.TechnicianScheduleResponse;

/**
 * Assembler for converting between TechnicianSchedule-related requests, commands, and responses.
 */
public class TechnicianScheduleAssembler {

    /**
     * Converts a CreateTechnicianScheduleRequest into a CreateTechnicianScheduleCommand.
     * Technician must already be loaded (passed as argument from the service).
     */
    public static CreateTechnicianScheduleCommand toCommandFromRequest(
            CreateTechnicianScheduleRequest request,
            Technician technician) {

        return new CreateTechnicianScheduleCommand(
                technician,
                request.dayOfWeek(),
                request.startTime(),
                request.endTime(),
                request.isActive()
        );
    }

    /**
     * Converts an UpdateTechnicianScheduleRequest into an UpdateTechnicianScheduleCommand.
     */
    public static UpdateTechnicianScheduleCommand toCommandFromRequest(
            Long technicianScheduleId,
            UpdateTechnicianScheduleRequest request) {

        return new UpdateTechnicianScheduleCommand(
                technicianScheduleId,
                request.dayOfWeek(),
                request.startTime(),
                request.endTime(),
                request.isActive()
        );
    }

    /**
     * Converts a TechnicianSchedule entity into a TechnicianScheduleResponse.
     */
    public static TechnicianScheduleResponse toResponseFromEntity(TechnicianSchedule entity) {
        return new TechnicianScheduleResponse(
                entity.getId().toString(),
                entity.getTechnician().getId().toString(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getIsActive()
        );
    }
}
